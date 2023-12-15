package com.oskarwiedeweg.cloudwork.auth.dto;

import com.oskarwiedeweg.cloudwork.user.UserDto;
import lombok.Data;

@Data
public class AuthenticationDto {

    private final String accessToken;
    private final UserDto userData;

    private final boolean needs2FA;
    private final String tempAuthKey;

    public AuthenticationDto(String accessToken, UserDto userData) {
        this.accessToken = accessToken;
        this.userData = userData;
        this.needs2FA = false;
        this.tempAuthKey = null;
    }

    public AuthenticationDto(String tempAuthKey) {
        this.accessToken = null;
        this.userData = null;
        this.needs2FA = true;
        this.tempAuthKey = tempAuthKey;
    }
}
