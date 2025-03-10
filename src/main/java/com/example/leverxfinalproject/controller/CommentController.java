package com.example.leverxfinalproject.controller;

import com.example.leverxfinalproject.dto.CommentRequest;
import com.example.leverxfinalproject.dto.CommentResponse;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{userId}/comments")
    public ResponseEntity<CommentResponse> save(
            @Valid @RequestBody CommentRequest request,
            @PathVariable Integer userId,
            @CookieValue(value = "author_id", required = false) String authorId,
            HttpServletResponse response) {
        if(authorId == null) {
            authorId = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("author_id", authorId);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        return ResponseEntity.ok(commentService.save(request, userId, authorId));
    }

    @GetMapping("/{userId}/comments")
    public ResponseEntity<List<CommentResponse>> findAll(@PathVariable Integer userId) {
        return ResponseEntity.ok(commentService.findAll(userId));
    }

    @GetMapping("/{userId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Integer userId, @PathVariable Integer commentId) {
        return ResponseEntity.ok(commentService.findById(userId, commentId));
    }

    @PutMapping("/{userId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @Valid @RequestBody CommentRequest request,
            @PathVariable Integer userId,
            @PathVariable Integer commentId,
            @CookieValue(value = "author_id") String authorId) {
        return ResponseEntity.ok(commentService.update(request, userId, commentId, authorId));
    }
    @DeleteMapping("/{userId}/comments/{commentId}")
    public void delete(
            @PathVariable Integer userId,
            @PathVariable Integer commentId,
            @CookieValue(value = "author_id") String authorId) {

        commentService.delete(userId, commentId, authorId);
    }
}
