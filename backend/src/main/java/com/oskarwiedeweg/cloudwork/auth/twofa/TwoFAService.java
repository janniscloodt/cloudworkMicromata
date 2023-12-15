package com.oskarwiedeweg.cloudwork.auth.twofa;

import com.oskarwiedeweg.cloudwork.BitUtils;
import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserDao;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.util.Utils;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.oskarwiedeweg.cloudwork.BitUtils.SettingBits.TWO_FACTOR_AUTH;

@Data
@Service
public class TwoFAService {

    @Value("${spring.application.name}")
    private String appName;

    private final SecretGenerator secretGenerator;
    private final CodeVerifier codeVerifier;
    private final TempTokenDao tempTokenDao;
    private final QrGenerator qrGenerator;
    private final UserDao userDao;

    @SneakyThrows
    public String setup2FA(User user) {
        if (BitUtils.hasBit(user.getSettings(), TWO_FACTOR_AUTH.getBit())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already set up 2FA!");
        }

        String user2FASecret = secretGenerator.generate();

        userDao.updateUserSettingsWith2FASecret(
                user.getId(),
                BitUtils.addBit(user.getSettings(), TWO_FACTOR_AUTH.getBit()),
                user2FASecret
        );

        return generateQRCode(user.getEmail(), user2FASecret);
    }

    public boolean has2FAEnabled(User user) {
        return BitUtils.hasBit(user.getSettings(), TWO_FACTOR_AUTH.getBit());
    }

    public String create2FAChallenge(User user) {
        String token = secretGenerator.generate();
        tempTokenDao.saveTempToken(token, user.getId());
        return token;
    }

    private String generateQRCode(String userMail, String user2FASecret) throws QrGenerationException {
        QrData data = new QrData.Builder()
                .secret(user2FASecret)
                .label(userMail)
                .algorithm(HashingAlgorithm.SHA256)
                .issuer(appName)
                .digits(6)
                .period(30)
                .build();
        byte[] qrCode = qrGenerator.generate(data);

        return Utils.getDataUriForImage(qrCode, qrGenerator.getImageMimeType());
    }

    public User validate(String token, Long twoFACode) {
        User user = tempTokenDao.retrieveTempToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Temp token is invalid"));

        if (!has2FAEnabled(user)) {
            return user;
        }

        if (!codeVerifier.isValidCode(user.getTwoFASecret(), twoFACode.toString())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "2FA Code is invalid!");
        }

        tempTokenDao.deleteTempToken(token);
        return user;
    }
}
