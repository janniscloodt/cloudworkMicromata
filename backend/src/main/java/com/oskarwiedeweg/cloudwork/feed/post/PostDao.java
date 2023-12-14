package com.oskarwiedeweg.cloudwork.feed.post;

import lombok.Data;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Optional<Post> findPostById(Long postId) {
        try {
            Post post = jdbcTemplate.queryForObject("select posts.*, users.name as user_name from posts inner join users on posts.user_id = users.id where posts.id = ?", rowMapper, postId);
            return Optional.ofNullable(post);
        } catch (IncorrectResultSizeDataAccessException ok) {
            return Optional.empty();
        }
    }

    public boolean deletePostById(Long postId) {
        return jdbcTemplate.update("delete from posts where id = ?", postId) == 1;
    }

    public boolean updatePost(Long postId, String title, String description) {
        return jdbcTemplate.update("update posts set title = ?, description = ? where id = ?", title, description, postId) == 1;
    }
}
