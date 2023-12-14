package com.oskarwiedeweg.cloudwork;

import com.oskarwiedeweg.cloudwork.feed.dto.PostDto;
import com.oskarwiedeweg.cloudwork.feed.post.Post;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Post, PostDto> postDaoTypeMap = modelMapper.typeMap(Post.class, PostDto.class);
        postDaoTypeMap.addMapping(post -> post.getUser().getId(), (post, userId) -> post.setAuthorId((Long) userId));
        return modelMapper;
    }

}
