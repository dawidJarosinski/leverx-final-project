package com.example.leverxfinalproject.dto;

import java.time.LocalDateTime;

public record UserResponse(String firstName, String lastName, String email, LocalDateTime createdAt, String role) {
}
