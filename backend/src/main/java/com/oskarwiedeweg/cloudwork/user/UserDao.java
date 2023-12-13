package com.oskarwiedeweg.cloudwork.user;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.Clock;
import java.time.LocalDate;

@Data
@Repository
public class UserDao {


    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper rowMapper;

    public Long saveUser(String username, String email, String password) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((con) -> {
            PreparedStatement preparedStatement = con.prepareStatement("insert into users (name, email, password, created_at) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setDate(4, Date.valueOf(LocalDate.now(Clock.systemUTC())));
            return preparedStatement;
        }, generatedKeyHolder);
        return generatedKeyHolder.getKeyAs(Long.class);
    }

}
