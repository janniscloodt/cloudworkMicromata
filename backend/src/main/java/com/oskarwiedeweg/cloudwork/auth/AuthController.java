package com.oskarwiedeweg.cloudwork.auth;

import com.oskarwiedeweg.cloudwork.auth.dto.AuthenticationDto;
import com.oskarwiedeweg.cloudwork.auth.dto.LoginDto;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationDto login(@RequestBody LoginDto body) {
        return authService.login(body);
    }

}