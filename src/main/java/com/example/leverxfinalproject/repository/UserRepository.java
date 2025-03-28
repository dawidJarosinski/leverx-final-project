package com.example.leverxfinalproject.repository;

import com.example.leverxfinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    boolean existsUserByEmail(String email);
    Optional<User> findUserById(Integer id);
}
