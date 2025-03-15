package com.example.leverxfinalproject.service;

import com.example.leverxfinalproject.dto.request.SellerProfileRequest;
import com.example.leverxfinalproject.dto.response.SellerProfileResponse;
import com.example.leverxfinalproject.enums.Role;
import com.example.leverxfinalproject.model.SellerProfile;
import com.example.leverxfinalproject.model.User;
import com.example.leverxfinalproject.repository.CommentRepository;
import com.example.leverxfinalproject.repository.SellerProfileRepository;
import com.example.leverxfinalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerProfileService {
    private final SellerProfileRepository sellerProfileRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public SellerProfileResponse save(SellerProfileRequest request, String email) {
        User user = userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("wrong username"));
        if(user.getRole() != Role.SELLER) {
            throw new IllegalArgumentException("this user is not a SELLER");
        }
        if(sellerProfileRepository.existsSellerProfileByUser(user)) {
            throw new IllegalArgumentException("this user already has seller profile");
        }
        SellerProfile sellerProfile = new SellerProfile(
                request.name(),
                user,
                false
        );
        sellerProfileRepository.save(sellerProfile);

        return SellerProfileResponse.from(sellerProfile);
    }

    public List<SellerProfileResponse> findAllSortedByRatingDescending() {
        return sellerProfileRepository
                .findAll()
                .stream()
                .filter(SellerProfile::isApproved)
                .sorted((sellerProfile1, sellerProfile2) -> {
                    return commentRepository.findAverageRatingBySellerProfile(sellerProfile2).intValue()
                            - commentRepository.findAverageRatingBySellerProfile(sellerProfile1).intValue();
                })
                .map(SellerProfileResponse::from)
                .toList();
    }
}
