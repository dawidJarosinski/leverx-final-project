package com.example.leverxfinalproject.dto.response;

import com.example.leverxfinalproject.model.SellerProfile;

public record SellerProfileResponse(Integer id, String name, Integer userId, boolean approved) {
    public static SellerProfileResponse from(SellerProfile sellerProfile) {
        return new SellerProfileResponse(
                sellerProfile.getId(),
                sellerProfile.getName(),
                sellerProfile.getUser() == null ? 0 : sellerProfile.getUser().getId(),
                sellerProfile.isApproved()
        );
    }
}
