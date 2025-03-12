package com.example.leverxfinalproject.service;

import com.example.leverxfinalproject.dto.request.SellerProfileRequest;
import com.example.leverxfinalproject.dto.response.SellerProfileResponse;
import com.example.leverxfinalproject.enums.Role;
import com.example.leverxfinalproject.model.SellerProfile;
import com.example.leverxfinalproject.model.User;
import com.example.leverxfinalproject.repository.SellerProfileRepository;
import com.example.leverxfinalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerProfileService {
    private final SellerProfileRepository sellerProfileRepository;
    private final UserRepository userRepository;

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

}
