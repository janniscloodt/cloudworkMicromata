package com.oskarwiedeweg.cloudwork.auth.dto;

import lombok.Data;

@Data
public class LoginDto {
    private final String username;
    private final String password;
}
