package com.oskarwiedeweg.cloudwork.auth.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private Long userId;
    private String jwtToken;

    public JwtAuthenticationToken(Long userId, String jwtToken) {
        super(Collections.emptyList());
        this.userId = userId;
        this.jwtToken = jwtToken;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }

    @Override
    public Long getPrincipal() {
        return userId;
    }
}
