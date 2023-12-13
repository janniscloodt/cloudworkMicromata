package com.oskarwiedeweg.cloudwork.feed;

import com.oskarwiedeweg.cloudwork.feed.dto.FeedDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/feed")
public record FeedController(FeedService feedService) {

    @GetMapping
    public FeedDto getFeed() {
        return feedService.getFeed();
    }

}
