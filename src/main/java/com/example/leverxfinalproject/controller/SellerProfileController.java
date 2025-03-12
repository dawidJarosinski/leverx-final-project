package com.example.leverxfinalproject.controller;


import com.example.leverxfinalproject.dto.request.SellerProfileRequest;
import com.example.leverxfinalproject.dto.response.SellerProfileResponse;
import com.example.leverxfinalproject.service.SellerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SellerProfileController {

    private final SellerProfileService sellerProfileService;

    @PostMapping("/profiles")
    public ResponseEntity<SellerProfileResponse> save(
            @Valid @RequestBody SellerProfileRequest request,
            Principal principal) {
        return ResponseEntity.ok(sellerProfileService.save(request, principal.getName()));
    }
}
