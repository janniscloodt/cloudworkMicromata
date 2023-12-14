package com.oskarwiedeweg.cloudwork.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

}
