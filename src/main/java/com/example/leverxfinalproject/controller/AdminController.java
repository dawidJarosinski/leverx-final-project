package com.example.leverxfinalproject.controller;

import com.example.leverxfinalproject.dto.CommentResponse;
import com.example.leverxfinalproject.dto.UserResponse;
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

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> findAllUsersToApprove() {
        return ResponseEntity.ok(adminService.findAllUsersToConfirm());
    }

    @PatchMapping("/users/{userId}/approve")
    public ResponseEntity<UserResponse> approveUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(adminService.approveUser(userId));
    }

    @DeleteMapping("/users/{userId}/decline")
    public void declineUser(@PathVariable Integer userId) {
        adminService.declineUser(userId);
    }


}
