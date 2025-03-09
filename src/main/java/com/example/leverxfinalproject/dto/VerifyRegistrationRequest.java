package com.example.leverxfinalproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyRegistrationRequest(@NotBlank @Email String email, @NotBlank String code) {
}
