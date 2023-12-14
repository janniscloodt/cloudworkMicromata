package com.oskarwiedeweg.cloudwork.auth.dto;

import lombok.Data;

@Data
public class RegisterDto {

    private final String username;
    private final String email;
    private final String password;

}
