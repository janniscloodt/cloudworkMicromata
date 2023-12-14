package com.oskarwiedeweg.cloudwork.feed;

import com.oskarwiedeweg.cloudwork.feed.dto.CreatePostDto;
import com.oskarwiedeweg.cloudwork.feed.dto.FeedDto;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/v1/feed")
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    public FeedDto getFeed() {
        return feedService.getFeed();
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public void createPost(@AuthenticationPrincipal Long userId, @Valid @RequestBody CreatePostDto body) {
        feedService.createPost(userId, body);
    }

}
