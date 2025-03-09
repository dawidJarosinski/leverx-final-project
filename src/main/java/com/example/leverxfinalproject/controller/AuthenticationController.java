package com.example.leverxfinalproject.controller;


import com.example.leverxfinalproject.dto.LoginRequest;
import com.example.leverxfinalproject.dto.RegistrationRequest;
import com.example.leverxfinalproject.dto.UserResponse;
import com.example.leverxfinalproject.dto.VerifyRegistrationRequest;
import com.example.leverxfinalproject.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
