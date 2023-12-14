package com.oskarwiedeweg.cloudwork.auth.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Data
@Slf4j
@Component
public class TokenFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!authorizationHeader.startsWith(BEARER_PREFIX)) {
            log.debug("Authorization header not prefixed with '{}'", BEARER_PREFIX);
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length());
        if (!tokenService.isTokenValid(token)) {
            log.debug("Invalid jwt token '{}'", token);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalid or expired.");
        }

        Long userId = tokenService.getUserId(token);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(userId, token);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        log.debug("Token valid. Set authentication.");

        filterChain.doFilter(request, response);
    }

}
