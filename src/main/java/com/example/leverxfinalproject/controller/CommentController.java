package com.example.leverxfinalproject.controller;

import com.example.leverxfinalproject.dto.request.CommentRequest;
import com.example.leverxfinalproject.dto.request.CommentWithProfileRequest;
import com.example.leverxfinalproject.dto.response.CommentResponse;
import com.example.leverxfinalproject.service.CommentService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/profiles/{profileId}/comments")
    public ResponseEntity<CommentResponse> save(
            @Valid @RequestBody CommentRequest request,
            @PathVariable Integer profileId,
            @CookieValue(value = "author_id", required = false) String authorId,
            HttpServletResponse response) {
        if(authorId == null) {
            authorId = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("author_id", authorId);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        return ResponseEntity.ok(commentService.save(request, profileId, authorId));
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentResponse> saveWithProfile(
            @Valid @RequestBody CommentWithProfileRequest request,
            @CookieValue(value = "author_id", required = false) String authorId,
            HttpServletResponse response) {
        if(authorId == null) {
            authorId = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("author_id", authorId);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        return ResponseEntity.ok(commentService.saveWithProfile(request, authorId));
    }

    @GetMapping("/profiles/{profileId}/comments")
    public ResponseEntity<List<CommentResponse>> findAll(@PathVariable Integer profileId) {
        return ResponseEntity.ok(commentService.findAll(profileId));
    }

    @GetMapping("/profiles/{profileId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Integer profileId, @PathVariable Integer commentId) {
        return ResponseEntity.ok(commentService.findById(profileId, commentId));
    }

    @PutMapping("/profiles/{profileId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> update(
            @Valid @RequestBody CommentRequest request,
            @PathVariable Integer profileId,
            @PathVariable Integer commentId,
            @CookieValue(value = "author_id") String authorId) {
        return ResponseEntity.ok(commentService.update(request, profileId, commentId, authorId));
    }
    @DeleteMapping("/profiles/{userId}/comments/{commentId}")
    public void delete(
            @PathVariable Integer userId,
            @PathVariable Integer commentId,
            @CookieValue(value = "author_id") String authorId) {

        commentService.delete(userId, commentId, authorId);
    }
}
