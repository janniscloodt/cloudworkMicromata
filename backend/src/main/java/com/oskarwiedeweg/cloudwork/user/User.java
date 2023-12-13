package com.oskarwiedeweg.cloudwork.user;

import lombok.Data;

@Data
public class User {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
}
