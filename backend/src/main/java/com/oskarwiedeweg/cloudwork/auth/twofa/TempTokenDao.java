package com.oskarwiedeweg.cloudwork.auth.twofa;

import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserRowMapper;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Data
@Repository
public class TempTokenDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public void saveTempToken(String token, Long userId) {
        jdbcTemplate.update("insert into user_temp_token (token, user_id) VALUES (?, ?)", token, userId);
    }

    public Optional<User> retrieveTempToken(String token) {
        List<User> query = jdbcTemplate.query("select u.* from user_temp_token inner join users u on u.id = user_temp_token.user_id where token = ?", userRowMapper, token);
        if (query.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(query.get(0));
    }

    public void deleteTempToken(String token) {
        jdbcTemplate.update("delete from user_temp_token where token = ?", token);
    }
}
