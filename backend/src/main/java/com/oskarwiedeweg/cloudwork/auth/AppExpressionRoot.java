package com.oskarwiedeweg.cloudwork.auth;

import com.oskarwiedeweg.cloudwork.auth.c4.C4MethodSecurityExpressionRoot;
import com.oskarwiedeweg.cloudwork.feed.post.Post;
import com.oskarwiedeweg.cloudwork.feed.post.PostDao;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;

import java.util.Optional;

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
        Object principal = getPrincipal();
        if (principal instanceof Long id) {
            return id;
        }
        throw new RuntimeException();
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
