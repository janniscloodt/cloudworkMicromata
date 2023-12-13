package com.oskarwiedeweg.cloudwork.auth.dto;

import com.oskarwiedeweg.cloudwork.user.UserDto;
import lombok.Data;

@Data
public class AuthenticationDto {

    private final String accessToken;
    private final UserDto userData;

}
