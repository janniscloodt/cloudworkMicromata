package com.oskarwiedeweg.cloudwork.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    private UserService underTest;

    @BeforeEach
    public void setup() {
        userDao = mock(UserDao.class);
        passwordEncoder = mock(PasswordEncoder.class);
        underTest = new UserService(userDao, passwordEncoder);
    }

    @Test
    void testCreateUser() {
        //given
        String name = "test";
        String email = "test@gmail.com";
        String password = "test";
        String encodedPassword = "encoded";

        Long id = 1L;

        //when
        when(userDao.saveUser(name, email, encodedPassword))
                .thenReturn(id);
        when(passwordEncoder.encode(password))
                .thenReturn(encodedPassword);

        //then
        Long user = underTest.createUser(name, email, password);

        assertEquals(user, id);
        verify(userDao).saveUser(name, email, encodedPassword);
    }

    @Test
    void testGetUserByName() {
        //given
        String username = "test";
        User user = mock(User.class);

        //when
        when(userDao.findUserByName(username)).thenReturn(Optional.of(user));

        //then
        Optional<User> userByName = underTest.getUserByName(username);

        assertTrue(userByName.isPresent());
        assertEquals(userByName.get(), user);
    }
}