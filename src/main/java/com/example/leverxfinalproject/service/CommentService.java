package com.example.leverxfinalproject.service;

import com.example.leverxfinalproject.dto.request.CommentRequest;
import com.example.leverxfinalproject.dto.request.CommentWithProfileRequest;
import com.example.leverxfinalproject.dto.response.CommentResponse;
import com.example.leverxfinalproject.model.Comment;
import com.example.leverxfinalproject.model.SellerProfile;
import com.example.leverxfinalproject.repository.CommentRepository;
import com.example.leverxfinalproject.repository.SellerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final SellerProfileRepository sellerProfileRepository;

    @Transactional
    public CommentResponse save(CommentRequest request, Integer profileId, String authorId) {
        SellerProfile sellerProfile = sellerProfileRepository
                .findSellerProfileById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("wrong seller profile id"));

        if(!sellerProfile.isApproved()) {
            throw new IllegalArgumentException("this seller is not approved");
        }

        Comment comment = new Comment(
                authorId,
                request.message(),
                request.rating(),
                LocalDateTime.now(),
                false,
                sellerProfile
        );
        commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    @Transactional
    public CommentResponse saveWithProfile(CommentWithProfileRequest request, String authorId) {
        SellerProfile sellerProfile = new SellerProfile(request.sellerProfile().name(), null, false);
        Comment comment = new Comment(
                authorId,
                request.message(),
                request.rating(),
                LocalDateTime.now(),
                false,
                sellerProfile
        );

        commentRepository.save(comment);
        return CommentResponse.from(comment);
    }

    public List<CommentResponse> findAll(Integer profileId) {
        SellerProfile sellerProfile = sellerProfileRepository
                .findSellerProfileById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("wrong seller profile id"));

        return commentRepository
                .findCommentsBySellerProfileAndApproved(sellerProfile, true)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    public CommentResponse findById(Integer profileId, Integer commentId) {
        SellerProfile sellerProfile = sellerProfileRepository
                .findSellerProfileById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("wrong seller profile id"));

        Comment comment = commentRepository
                .findCommentById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("wrong comment id"));
        if(!comment.getSellerProfile().getId().equals(sellerProfile.getId())) {
            throw new IllegalArgumentException("wrong comment if for this seller profile");
        }
        return CommentResponse.from(comment);
    }

    @Transactional
    public CommentResponse update(CommentRequest request, Integer profileId, Integer commentId, String authorId) {
        Comment comment = validateCommentOwnershipAndIsApproved(profileId, commentId, authorId);

        comment.setApproved(false);
        comment.setRating(request.rating());
        comment.setMessage(request.message());

        commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    @Transactional
    public void delete(Integer profileId, Integer commentId, String authorId) {
        Comment comment = validateCommentOwnershipAndIsApproved(profileId, commentId, authorId);

        commentRepository.delete(comment);
    }

    private Comment validateCommentOwnershipAndIsApproved(Integer profileId, Integer commentId, String authorId) {
        Comment comment = commentRepository
                .findCommentById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("wrong comment id"));
        if(!comment.isApproved()) {
            throw new IllegalArgumentException("comment has to be approved");
        }
        if(!comment.getAuthorId().equals(authorId)) {
            throw new IllegalArgumentException("you are not the author of the comment");
        }

        if(!comment.getSellerProfile().getId().equals(profileId)) {
            throw new IllegalArgumentException("wrong comment id for this seller profile");
        }
        return comment;
    }
}
