package com.oskarwiedeweg.cloudwork.auth.token;

import com.oskarwiedeweg.cloudwork.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Data
@Service
public class TokenService {

    @Value("${jwt.key}")
    private String jwtKey;

    @SneakyThrows
    public String generateToken(User user) {
        Instant now = Instant.now();
        Date from  = Date.from(now);
        Date exp = Date.from(now.plus(30, ChronoUnit.DAYS));

        SecretKey secretKey = generateSecretKey();

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("username", user.getName())
                .claim("email", user.getEmail())
                .expiration(exp)
                .issuedAt(from)
                .signWith(secretKey)
                .compact();
    }

    private SecretKey generateSecretKey() {
        return Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(generateSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(token);
        String subject = claimsJws.getPayload().getSubject();
        return Long.parseLong(subject);
    }


}
