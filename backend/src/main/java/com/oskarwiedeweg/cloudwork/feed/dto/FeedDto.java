package com.oskarwiedeweg.cloudwork.feed.dto;

import com.oskarwiedeweg.cloudwork.user.UserDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FeedDto {

    private final List<PostDto> posts;
    private final Map<Long, UserDto> authors;

}
