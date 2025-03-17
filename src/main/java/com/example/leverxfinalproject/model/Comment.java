package com.example.leverxfinalproject.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "comments")
@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "author_id")
    private UUID authorId;

    @Column(name = "message")
    private String message;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "approved")
    private boolean approved;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seller_profile_id")
    private SellerProfile sellerProfile;

    public Comment(UUID authorId, String message, Integer rating, LocalDateTime createdAt, boolean approved, SellerProfile sellerProfile) {
        this.authorId = authorId;
        this.message = message;
        this.rating = rating;
        this.createdAt = createdAt;
        this.approved = approved;
        this.sellerProfile = sellerProfile;
    }
}
