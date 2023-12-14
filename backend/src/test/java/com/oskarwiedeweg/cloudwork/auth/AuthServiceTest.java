package com.oskarwiedeweg.cloudwork.auth;

import com.oskarwiedeweg.cloudwork.auth.dto.AuthenticationDto;
import com.oskarwiedeweg.cloudwork.auth.dto.LoginDto;
import com.oskarwiedeweg.cloudwork.auth.token.TokenService;
import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserDto;
import com.oskarwiedeweg.cloudwork.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService underTest;

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private ModelMapper modelMapper;
    private UserService userService;

    @BeforeEach
    public void setup() {
        authenticationManager = mock(AuthenticationManager.class);
        tokenService = mock(TokenService.class);
        modelMapper = mock(ModelMapper.class);
        userService = mock(UserService.class);
        underTest = new AuthService(authenticationManager, tokenService, modelMapper, userService);
    }

    @Test
    void testSuccessfulLogin() {
        //given
        LoginDto loginDto = new LoginDto("test", "test");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);
        UserUserDetails userDetails = mock(UserUserDetails.class);
        String jwt = "key";

        //when
        when(userDetails.getUser()).thenReturn(user);
        when(authenticationManager.authenticate(token))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, null));
        when(tokenService.generateToken(user)).thenReturn(jwt);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        // then
        AuthenticationDto login = underTest.login(loginDto);

        assertEquals(jwt, login.getAccessToken());
        assertEquals(userDto, login.getUserData());

        verify(authenticationManager).authenticate(token);
        verify(tokenService).generateToken(user);
    }

    @Test
    void testInvalidCredentialsLogin() {
        //given
        LoginDto loginDto = new LoginDto("test", "test");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);
        UserUserDetails userDetails = mock(UserUserDetails.class);
        String jwt = "key";

        //when
        when(userDetails.getUser()).thenReturn(user);
        when(authenticationManager.authenticate(token))
                .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials"));
        when(tokenService.generateToken(user)).thenReturn(jwt);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        // then
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> underTest.login(loginDto));

        assertEquals(responseStatusException.getStatusCode(), HttpStatus.UNAUTHORIZED);

        verify(authenticationManager).authenticate(token);
        verify(tokenService, never()).generateToken(user);
        verify(modelMapper, never()).map(user, UserDto.class);
    }

    @Test
    void testUnexpectedPrincipalLogin() {
        //given
        LoginDto loginDto = new LoginDto("test", "test");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);
        Object userDetails = mock(Object.class);
        String jwt = "key";

        //when
        when(authenticationManager.authenticate(token))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, null));
        when(tokenService.generateToken(user)).thenReturn(jwt);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        // then
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> underTest.login(loginDto));

        assertEquals(runtimeException.getMessage(), "User Details are not expected UserUserDetails!");
    }
}