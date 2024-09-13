package com.instagram.response;
import lombok.Data;

@Data
public class CommentRequest {

    private String userId;
    private String comment;
    private String timestamp;

    // Getters and Setters
}