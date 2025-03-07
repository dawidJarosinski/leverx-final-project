package com.example.leverxfinalproject.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "role")
    private Role role;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "approved")
    private boolean approved;

    public User(String firstName, String lastName, String password, String email, LocalDateTime createdAt, Role role, boolean enabled, boolean approved) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.role = role;
        this.enabled = enabled;
        this.approved = approved;
    }
}
