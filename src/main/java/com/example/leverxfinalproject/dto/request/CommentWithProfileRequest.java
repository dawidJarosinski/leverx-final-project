package com.example.leverxfinalproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public record CommentWithProfileRequest(@Range(min = 1, max = 5) Integer rating, String message, SellerProfile sellerProfile) {
    public record SellerProfile(@NotBlank String name) {}
}
