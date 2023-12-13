package com.oskarwiedeweg.cloudwork.user;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class UserService {

    private final UserDao userDao;

    public Long createUser(String name, String email, String password) {
        return userDao.saveUser(name, email, password);
    }

    public Optional<User> getUserByName(String username) {
        return userDao.findUserByName(username);
    }

}
