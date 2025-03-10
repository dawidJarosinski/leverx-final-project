package com.example.leverxfinalproject.dto;

import java.time.LocalDateTime;

public record CommentResponse(String authorId, Integer rating, String message, LocalDateTime createdAt, boolean approved) {
}
