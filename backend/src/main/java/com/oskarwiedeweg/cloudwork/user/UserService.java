package com.oskarwiedeweg.cloudwork.user;

import com.oskarwiedeweg.cloudwork.exception.DuplicateUserException;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public Long createUser(String name, String email, String password) throws DuplicateUserException {
        String encoded = passwordEncoder.encode(password);
        return userDao.saveUser(name, email, encoded);
    }

    public Optional<User> getUserByName(String username) {
        return userDao.findUserByName(username);
    }

}
