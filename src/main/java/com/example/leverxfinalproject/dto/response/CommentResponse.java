package com.example.leverxfinalproject.dto.response;

import com.example.leverxfinalproject.model.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Integer id,
        String authorId,
        Integer rating,
        String message,
        LocalDateTime createdAt,
        boolean approved) {

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getAuthorId(),
                comment.getRating(),
                comment.getMessage(),
                comment.getCreatedAt(),
                comment.isApproved()
        );
    }
}
