package com.oskarwiedeweg.cloudwork.feed.post;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Repository
public class PostDao {
    private final JdbcTemplate jdbcTemplate;
    private final PostRowMapper rowMapper;

    public List<Post> getPosts() {
        return jdbcTemplate.query("select posts.*, users.name as user_name from posts inner join users on posts.user_id = users.id order by posts.published_at desc", rowMapper);
    }

    public void savePost(Long userId, String title, String description) {
        jdbcTemplate.update("insert into posts (user_id, title, description, published_at) values (?, ?, ?, ?)", userId, title, description, Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
    }

}
