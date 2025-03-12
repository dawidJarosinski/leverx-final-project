package com.example.leverxfinalproject.controller;

import com.example.leverxfinalproject.dto.response.CommentResponse;
import com.example.leverxfinalproject.dto.response.SellerProfileResponse;
import com.example.leverxfinalproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponse>> findAllCommentsToApprove() {
        return ResponseEntity.ok(adminService.findAllCommentsToApprove());
    }

    @PatchMapping("/comments/{commentId}/approve")
    public ResponseEntity<CommentResponse> approveComment(@PathVariable Integer commentId) {
        return ResponseEntity.ok(adminService.approveComment(commentId));
    }

    @DeleteMapping("/comments/{commentId}/decline")
    public void declineComment(@PathVariable Integer commentId) {
        adminService.declineComment(commentId);
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<SellerProfileResponse>> findAllProfilesToApprove() {
        return ResponseEntity.ok(adminService.findAllSellerProfilesToApprove());
    }

    @PatchMapping("/profiles/{profileId}/approve")
    public ResponseEntity<SellerProfileResponse> approveProfile(@PathVariable Integer profileId) {
        return ResponseEntity.ok(adminService.approveProfile(profileId));
    }

    @DeleteMapping("/profiles/{profileId}/decline")
    public void declineProfile(@PathVariable Integer profileId) {
        adminService.declineProfile(profileId);
    }


}
