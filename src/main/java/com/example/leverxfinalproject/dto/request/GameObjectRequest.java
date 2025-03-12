package com.example.leverxfinalproject.dto.request;

import jakarta.validation.constraints.NotBlank;

public record GameObjectRequest(
        @NotBlank String title,
        @NotBlank String text
) {
}
