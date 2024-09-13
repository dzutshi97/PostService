package com.instagram.service;

import com.instagram.models.Comment;
import com.instagram.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<Comment> fetchComments(String postId, int page, int pageSize){
        return commentRepository.fetchCommentsByPostId(postId, page, pageSize);
    }

    public String addComment(String postId, String comment) throws Exception {
        Comment cmnt = new Comment();
        cmnt.setCommentId(UUID.randomUUID().toString());
        cmnt.setComment(comment);
        cmnt.setTimestamp(System.currentTimeMillis());
        cmnt.setPostId(postId);
        return commentRepository.saveComment(postId,cmnt);
    }

    public void deleteComment(String postId, String commentId) throws Exception {
        commentRepository.deleteComment(postId,commentId);
    }

}
