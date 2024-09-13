package com.instagram.service;

import com.instagram.models.Comment;
import com.instagram.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);


    public List<Comment> fetchComments(String postId, int page, int pageSize){
        logger.info("fetch comments");
        return commentRepository.fetchCommentsByPostId(postId, page, pageSize);
    }

    public String addComment(String postId, String comment) throws Exception {
        logger.info("add comment");
        Comment cmnt = new Comment();
        cmnt.setCommentId(UUID.randomUUID().toString());
        cmnt.setComment(comment);
        cmnt.setTimestamp(System.currentTimeMillis());
        cmnt.setPostId(postId);
        return commentRepository.saveComment(postId,cmnt);
    }

    public void deleteComment(String postId, String commentId) throws Exception {
        logger.info("delete comment");
        commentRepository.deleteComment(postId,commentId);
    }

}
