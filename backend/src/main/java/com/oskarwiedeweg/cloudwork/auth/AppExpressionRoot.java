package com.oskarwiedeweg.cloudwork.auth;

import com.oskarwiedeweg.cloudwork.auth.c4.C4MethodSecurityExpressionRoot;
import com.oskarwiedeweg.cloudwork.feed.post.Post;
import com.oskarwiedeweg.cloudwork.feed.post.PostDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@Slf4j
public class AppExpressionRoot extends C4MethodSecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final PostDao postDao;

    public AppExpressionRoot(PostDao postDao) {
        this.postDao = postDao;
    }

    public boolean ownsPost(Long postId) {
        Optional<Post> postById = postDao.findPostById(postId);
        return postById.map(post -> getUserId().equals(post.getUser().getId())).orElse(true);

    }

    private Long getUserId() {
        Authentication authentication = getAuthentication();
        if (authentication == null || isAnonymous()) {
            return -1L;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof Long id) {
            return id;
        }
        log.warn("Authentication principal ist not user id!");
        return -1L;
    }


    @Override
    public void setFilterObject(Object filterObject) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }
}
