package com.oskarwiedeweg.cloudwork.feed.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime timestamp;

}
