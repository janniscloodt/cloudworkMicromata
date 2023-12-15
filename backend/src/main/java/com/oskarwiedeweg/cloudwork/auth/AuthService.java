package com.oskarwiedeweg.cloudwork.auth;

import com.oskarwiedeweg.cloudwork.auth.dto.AuthenticationDto;
import com.oskarwiedeweg.cloudwork.auth.dto.LoginDto;
import com.oskarwiedeweg.cloudwork.auth.dto.RegisterDto;
import com.oskarwiedeweg.cloudwork.auth.token.TokenService;
import com.oskarwiedeweg.cloudwork.auth.twofa.TwoFAService;
import com.oskarwiedeweg.cloudwork.exception.DuplicateUserException;
import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserDto;
import com.oskarwiedeweg.cloudwork.user.UserService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Data
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final TwoFAService twoFAService;

    public AuthenticationDto login(LoginDto loginDto) {
        String username = transformUsername(loginDto.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, loginDto.getPassword());

        Authentication authenticated;
        try {
            authenticated = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }

        if (!(authenticated.getPrincipal() instanceof UserUserDetails userDetails)) {
            throw new RuntimeException("User Details are not expected UserUserDetails!");
        }

        User user = userDetails.getUser();

        if (twoFAService.has2FAEnabled(user)) {
            return new AuthenticationDto(twoFAService.create2FAChallenge(user));
        }

        return createAuthenticationDto(user);
    }

    private AuthenticationDto createAuthenticationDto(User user) {
        String token = tokenService.generateToken(user);

        return new AuthenticationDto(token, modelMapper.map(user, UserDto.class));
    }

    public AuthenticationDto register(RegisterDto body) {
        String username = transformUsername(body.getUsername());


        Long userId;
        try {
            userId = userService.createUser(username, body.getEmail(), body.getPassword());
        } catch (DuplicateUserException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate username '%s'".formatted(username));
        }

        User tempUser = User.builder()
                .id(userId)
                .email(body.getEmail())
                .name(username)
                .build();
        return createAuthenticationDto(tempUser);
    }

    public AuthenticationDto verify(String token, Long twoFACode) {
        User user = twoFAService.validate(token, twoFACode);
        return createAuthenticationDto(user);
    }

    private String transformUsername(String username) {
        return username.toLowerCase();
    }


}
