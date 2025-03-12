package com.example.leverxfinalproject.service;

import com.example.leverxfinalproject.dto.response.CommentResponse;
import com.example.leverxfinalproject.dto.response.SellerProfileResponse;
import com.example.leverxfinalproject.model.Comment;
import com.example.leverxfinalproject.model.SellerProfile;
import com.example.leverxfinalproject.repository.CommentRepository;
import com.example.leverxfinalproject.repository.SellerProfileRepository;
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
    private final SellerProfileRepository sellerProfileRepository;

    public List<CommentResponse> findAllCommentsToApprove() {
        return commentRepository
                .findCommentsToApprove()
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional
    public CommentResponse approveComment(Integer commentId) {
        Comment comment = returnValidatedCommentById(commentId);

        if(!comment.getSellerProfile().isApproved()) {
            throw new IllegalArgumentException("first approve seller profile");
        }

        comment.setApproved(true);
        commentRepository.save(comment);
        return CommentResponse.from(comment);
    }


    @Transactional
    public void declineComment(Integer commentId) {
        Comment comment = returnValidatedCommentById(commentId);
        commentRepository.delete(comment);
    }


    public List<SellerProfileResponse> findAllSellerProfilesToApprove() {
        return sellerProfileRepository
                .findSellerProfilesToApprove()
                .stream()
                .map(SellerProfileResponse::from)
                .toList();
    }

    @Transactional
    public SellerProfileResponse approveProfile(Integer profileId) {
        SellerProfile sellerProfile = returnValidatedSellerProfileById(profileId);

        sellerProfile.setApproved(true);
        sellerProfileRepository.save(sellerProfile);

        return SellerProfileResponse.from(sellerProfile);
    }

    @Transactional
    public void declineProfile(Integer profileId) {
        SellerProfile sellerProfile = returnValidatedSellerProfileById(profileId);
        sellerProfileRepository.delete(sellerProfile);
    }


    private Comment returnValidatedCommentById(Integer commentId) {
        Comment comment = commentRepository
                .findCommentById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("wrong comment id"));
        if(comment.isApproved()) {
            throw new IllegalArgumentException("this comment is already approved");
        }
        return comment;
    }

    private SellerProfile returnValidatedSellerProfileById(Integer profileId) {
        SellerProfile sellerProfile = sellerProfileRepository
                .findSellerProfileById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("wrong profile id"));

        if(sellerProfile.isApproved()) {
            throw new IllegalArgumentException("this profile is already approved");
        }

        return sellerProfile;
    }


}
