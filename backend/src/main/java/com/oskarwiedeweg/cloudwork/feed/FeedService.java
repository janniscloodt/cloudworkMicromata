package com.oskarwiedeweg.cloudwork.feed;

import com.oskarwiedeweg.cloudwork.feed.dto.CreatePostDto;
import com.oskarwiedeweg.cloudwork.feed.dto.FeedDto;
import com.oskarwiedeweg.cloudwork.feed.dto.PostDto;
import com.oskarwiedeweg.cloudwork.feed.post.PostDao;
import com.oskarwiedeweg.cloudwork.user.UserDto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public void createPost(Long userId, CreatePostDto body) {
        postDao.savePost(userId, body.getTitle(), body.getDescription());
    }

    public void deletePost(Long postId) {
        if (!postDao.deletePostById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id '%s' not found!".formatted(postId));
        }
    }

    public void updatePost(Long postId, CreatePostDto body) {
        if (!postDao.updatePost(postId, body.getTitle(), body.getDescription())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id '%s' not found!".formatted(postId));
        }
    }
}
