package com.oskarwiedeweg.cloudwork.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterDto {

    @NotBlank
    @Pattern(regexp = "([A-Za-z0-9-._]{3,})")
    private final String username;

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])).{8,}")
    private final String password;

}
