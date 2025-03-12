package com.example.leverxfinalproject.dto.response;

import com.example.leverxfinalproject.model.GameObject;

import java.time.LocalDateTime;

public record GameObjectResponse(
        Integer id,
        String title,
        String text,
        LocalDateTime createdAt,
        LocalDateTime updateAt,
        Integer userId) {
    public static GameObjectResponse from(GameObject gameObject) {
        return new GameObjectResponse(
                gameObject.getId(),
                gameObject.getTitle(),
                gameObject.getText(),
                gameObject.getCreatedAt(),
                gameObject.getUpdatedAt(),
                gameObject.getUser().getId()
        );
    }
}
