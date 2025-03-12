package com.example.leverxfinalproject.dto.request;


import org.hibernate.validator.constraints.Range;

public record CommentRequest(@Range(min = 1, max = 5) Integer rating, String message) {
}
