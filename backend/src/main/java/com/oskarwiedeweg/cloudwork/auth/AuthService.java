package com.oskarwiedeweg.cloudwork.auth;

import com.oskarwiedeweg.cloudwork.auth.dto.AuthenticationDto;
import com.oskarwiedeweg.cloudwork.auth.dto.LoginDto;
import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Data
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;

    public AuthenticationDto login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authenticated;
        try {
            authenticated = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }

        if (!(authenticated.getPrincipal() instanceof UserUserDetails userDetails)) {
            throw new RuntimeException("User Details are not expected UserUserDetails!");
        }

        User user = userDetails.getUser();

        String token = tokenService.generateToken(user);

        return new AuthenticationDto(token, modelMapper.map(user, UserDto.class));
    }

}
