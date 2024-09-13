package com.instagram.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String commentId;
    private String postId;
    private String userId;
    private String comment;
    private long timestamp;

    // Getters, Setters, and other methods


    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}