package com.example.leverxfinalproject.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seller_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerProfile {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "approved")
    private boolean approved;

    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    public SellerProfile(String name, User user, boolean approved) {
        this.name = name;
        this.user = user;
        this.approved = approved;
    }
}
