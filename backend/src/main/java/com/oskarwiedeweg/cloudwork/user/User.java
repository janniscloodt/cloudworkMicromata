package com.oskarwiedeweg.cloudwork.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final LocalDate localDate;
}
