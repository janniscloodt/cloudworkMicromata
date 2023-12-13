package com.oskarwiedeweg.cloudwork.auth.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
