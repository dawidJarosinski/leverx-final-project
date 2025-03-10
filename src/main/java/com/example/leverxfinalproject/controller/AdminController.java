package com.example.leverxfinalproject.controller;

import com.example.leverxfinalproject.dto.CommentResponse;
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
    public ResponseEntity<List<CommentResponse>> findAllCommentsToConfirm() {
        return ResponseEntity.ok(adminService.findAllCommentsToConfirm());
    }

    @PatchMapping("/comments/{commentId}/approve")
    public ResponseEntity<CommentResponse> confirmComment(@PathVariable Integer commentId) {
        return ResponseEntity.ok(adminService.confirmComment(commentId));
    }

    @DeleteMapping("/comments/{commentId}/decline")
    public void declineComment(@PathVariable Integer commentId) {
        adminService.declineComment(commentId);
    }


}
