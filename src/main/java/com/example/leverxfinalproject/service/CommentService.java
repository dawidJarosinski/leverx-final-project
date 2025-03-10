package com.example.leverxfinalproject.service;

import com.example.leverxfinalproject.dto.CommentRequest;
import com.example.leverxfinalproject.dto.CommentResponse;
import com.example.leverxfinalproject.model.Comment;
import com.example.leverxfinalproject.model.User;
import com.example.leverxfinalproject.repository.CommentRepository;
import com.example.leverxfinalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse save(CommentRequest request, Integer userId, String authorId) {
        User user = userRepository
                .findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("wrong user id"));

        Comment comment = new Comment(
                authorId,
                request.message(),
                request.rating(),
                LocalDateTime.now(),
                false,
                user
        );
        commentRepository.save(comment);

        return mapCommentToCommentResponse(comment);
    }

    public List<CommentResponse> findAll(Integer userId) {
        User user = userRepository
                .findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("wrong user id"));

        return commentRepository
                .findCommentsByUserAndApproved(user, true)
                .stream()
                .map(this::mapCommentToCommentResponse)
                .toList();
    }

    public CommentResponse findById(Integer userId, Integer commentId) {
        User user = userRepository
                .findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("wrong user id"));

        Comment comment = commentRepository
                .findCommentById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("wrong comment id"));
        if(!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("wrong comment if for this user");
        }
        return mapCommentToCommentResponse(comment);
    }

    @Transactional
    public CommentResponse update(CommentRequest request, Integer userId, Integer commentId, String authorId) {
        Comment comment = validateCommentOwnershipAndIsApproved(userId, commentId, authorId);

        comment.setApproved(false);
        comment.setRating(request.rating());
        comment.setMessage(request.message());

        commentRepository.save(comment);

        return mapCommentToCommentResponse(comment);
    }

    @Transactional
    public void delete(Integer userId, Integer commentId, String authorId) {
        Comment comment = validateCommentOwnershipAndIsApproved(userId, commentId, authorId);

        commentRepository.delete(comment);
    }

    private Comment validateCommentOwnershipAndIsApproved(Integer userId, Integer commentId, String authorId) {
        Comment comment = commentRepository
                .findCommentById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("wrong comment id"));
        if(!comment.isApproved()) {
            throw new IllegalArgumentException("comment has to be approved");
        }
        if(!comment.getAuthorId().equals(authorId)) {
            throw new IllegalArgumentException("you are not the author of the comment");
        }

        if(!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("wrong comment id for this user");
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
