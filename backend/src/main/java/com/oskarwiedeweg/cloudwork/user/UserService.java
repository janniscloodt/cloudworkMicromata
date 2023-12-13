package com.oskarwiedeweg.cloudwork.user;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {

    private final UserDao userDao;

    public Long createUser(String name, String email, String password) {
        return userDao.saveUser(name, email, password);
    }

}
