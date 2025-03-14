package com.example.leverxfinalproject.controller;


import com.example.leverxfinalproject.dto.request.SellerProfileRequest;
import com.example.leverxfinalproject.dto.response.SellerProfileResponse;
import com.example.leverxfinalproject.service.SellerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class SellerProfileController {

    private final SellerProfileService sellerProfileService;

    @PostMapping()
    public ResponseEntity<SellerProfileResponse> save(
            @Valid @RequestBody SellerProfileRequest request,
            Principal principal) {
        return ResponseEntity.ok(sellerProfileService.save(request, principal.getName()));
    }

    @GetMapping()
    public ResponseEntity<List<SellerProfileResponse>> findAllSortedByRatingDescending() {
        return ResponseEntity.ok(sellerProfileService.findAllSortedByRatingDescending());
    }
}
