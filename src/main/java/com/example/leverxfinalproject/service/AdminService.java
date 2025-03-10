package com.example.leverxfinalproject.service;

import com.example.leverxfinalproject.dto.CommentResponse;
import com.example.leverxfinalproject.model.Comment;
import com.example.leverxfinalproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final CommentRepository commentRepository;

    public List<CommentResponse> findAllCommentsToConfirm() {
        return commentRepository
                .findCommentsToConfirm()
                .stream()
                .map(this::mapCommentToCommentResponse)
                .toList();
    }

    @Transactional
    public CommentResponse confirmComment(Integer commentId) {
        Comment comment = returnValidateCommentById(commentId);

        comment.setApproved(true);
        commentRepository.save(comment);
        return mapCommentToCommentResponse(comment);
    }


    @Transactional
    public void declineComment(Integer commentId) {
        Comment comment = returnValidateCommentById(commentId);
        commentRepository.delete(comment);
    }

    private Comment returnValidateCommentById(Integer commentId) {
        Comment comment = commentRepository
                .findCommentById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("wrong comment id"));
        if(comment.isApproved()) {
            throw new IllegalArgumentException("this comment is already approved");
        }
        return comment;
    }

    private CommentResponse mapCommentToCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getAuthorId(),
                comment.getRating(),
                comment.getMessage(),
                comment.getCreatedAt(),
                comment.isApproved()
        );
    }
}
