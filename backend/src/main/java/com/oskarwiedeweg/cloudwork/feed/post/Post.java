package com.oskarwiedeweg.cloudwork.feed.post;

import com.oskarwiedeweg.cloudwork.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    private final Long id;
    private final String title;
    private final String description;
    private final LocalDateTime timestamp;
    private final User user;

}
