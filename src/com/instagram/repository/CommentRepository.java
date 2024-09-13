package com.instagram.repository;

import com.instagram.models.Comment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface CommentRepository {

    public String saveComment(String postId, Comment comment) throws Exception;
    public void deleteComment(String postId, String commentId) throws Exception;
    public List<Comment> fetchCommentsByPostId(String postId, int page, int pageSize);
}
