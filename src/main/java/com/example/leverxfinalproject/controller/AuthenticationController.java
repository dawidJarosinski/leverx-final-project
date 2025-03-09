package com.example.leverxfinalproject.controller;


import com.example.leverxfinalproject.dto.*;
import com.example.leverxfinalproject.service.AuthenticationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/verify_code")
    public ResponseEntity<UserResponse> verifyCode(@Valid @RequestBody VerifyRegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.verifyCode(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/auth/forgot_password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(authenticationService.forgotPassword(request));
    }

    @PostMapping("/auth/reset")
    public ResponseEntity<UserResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authenticationService.resetPassword(request));
    }

    @GetMapping("/auth/check_code")
    public boolean checkCode(@Email @NotBlank @RequestParam String email, @NotBlank @RequestParam String code) {
        return authenticationService.checkCode(email, code);
    }

}
