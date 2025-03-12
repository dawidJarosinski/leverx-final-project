package com.example.leverxfinalproject.dto.response;


import com.example.leverxfinalproject.model.User;

import java.time.LocalDateTime;

public record UserResponse(
        Integer id,
        String firstName,
        String lastName,
        String email,
        LocalDateTime createdAt,
        String role) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getRole().name()
        );
    }
}
