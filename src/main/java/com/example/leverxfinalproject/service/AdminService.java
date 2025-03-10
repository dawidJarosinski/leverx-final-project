package com.example.leverxfinalproject.service;

import com.example.leverxfinalproject.dto.CommentResponse;
import com.example.leverxfinalproject.dto.UserResponse;
import com.example.leverxfinalproject.model.Comment;
import com.example.leverxfinalproject.model.User;
import com.example.leverxfinalproject.repository.CommentRepository;
import com.example.leverxfinalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<CommentResponse> findAllCommentsToApprove() {
        return commentRepository
                .findCommentsToApprove()
                .stream()
                .map(this::mapCommentToCommentResponse)
                .toList();
    }

    @Transactional
    public CommentResponse approveComment(Integer commentId) {
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

    public List<UserResponse> findAllUsersToConfirm() {
        return userRepository
                .findAllUsersToApprove()
                .stream()
                .map(this::mapUserToUserResponse)
                .toList();
    }

    @Transactional
    public UserResponse approveUser(Integer userId) {
        User user = returnValidateUserById(userId);

        user.setApproved(true);
        userRepository.save(user);

        return mapUserToUserResponse(user);
    }

    @Transactional
    public void declineUser(Integer userId) {
        User user = returnValidateUserById(userId);
        userRepository.delete(user);
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

    private User returnValidateUserById(Integer userId) {
        User user = userRepository
                .findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("wrong user id"));

        if(!user.isEnabled()) {
            throw new IllegalArgumentException("this user has to enable first");
        }

        if(user.isApproved()) {
            throw new IllegalArgumentException("this user is already approved");
        }
        return user;
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

    private UserResponse mapUserToUserResponse(User user) {
        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getRole().name()
        );
    }

}
