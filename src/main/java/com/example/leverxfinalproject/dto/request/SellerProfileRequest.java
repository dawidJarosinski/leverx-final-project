package com.example.leverxfinalproject.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SellerProfileRequest(@NotBlank String name) {
}
