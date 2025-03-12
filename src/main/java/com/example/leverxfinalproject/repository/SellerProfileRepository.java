package com.example.leverxfinalproject.repository;

import com.example.leverxfinalproject.model.SellerProfile;
import com.example.leverxfinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SellerProfileRepository extends JpaRepository<SellerProfile, Integer> {

    @Query("SELECT p FROM SellerProfile p WHERE p.approved = false")
    List<SellerProfile> findSellerProfilesToApprove();

    Optional<SellerProfile> findSellerProfileById(Integer id);

    boolean existsSellerProfileByUser(User user);

    boolean existsSellerProfileByApprovedAndUser_Id(boolean approved, Integer userId);
}
