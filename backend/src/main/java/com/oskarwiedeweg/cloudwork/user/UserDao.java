package com.oskarwiedeweg.cloudwork.user;

import com.oskarwiedeweg.cloudwork.exception.DuplicateUserException;
import lombok.Data;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

@Data
@Repository
public class UserDao {


    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper rowMapper;

    public Long saveUser(String username, String email, String password) throws DuplicateUserException {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update((con) -> {
                PreparedStatement preparedStatement = con.prepareStatement("insert into users (name, email, password, created_at) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.setDate(4, Date.valueOf(LocalDate.now(Clock.systemUTC())));
                return preparedStatement;
            }, generatedKeyHolder);
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserException();
        }

        return (Long) generatedKeyHolder.getKeys().get("id");
    }

    public Optional<User> findUserByName(String username) {
        try {
            User user = jdbcTemplate.queryForObject("select * from users where name = ?", rowMapper, username);
            return Optional.ofNullable(user);
        } catch (IncorrectResultSizeDataAccessException ok) {
            return Optional.empty();
        }
    }
}
