package com.example.leverxfinalproject.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(@NotBlank @Email String email, @NotBlank String code, @NotBlank String newPassword) {
}
