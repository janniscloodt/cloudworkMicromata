package com.oskarwiedeweg.cloudwork.feed;

import com.oskarwiedeweg.cloudwork.feed.dto.FeedDto;
import com.oskarwiedeweg.cloudwork.feed.dto.PostDto;
import com.oskarwiedeweg.cloudwork.feed.post.PostDao;
import com.oskarwiedeweg.cloudwork.user.UserDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class FeedService {

    private final PostDao postDao;
    private final ModelMapper modelMapper;

    public FeedDto getFeed() {
        Map<Long, UserDto> users = new HashMap<>();
        List<PostDto> posts = postDao.getPosts().stream()
                .peek(post -> users.put(post.getUser().getId(), modelMapper.map(post.getUser(), UserDto.class)))
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();

        return new FeedDto(posts, users);
    }

}
