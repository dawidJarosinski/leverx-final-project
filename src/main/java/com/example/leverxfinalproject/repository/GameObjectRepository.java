package com.example.leverxfinalproject.repository;

import com.example.leverxfinalproject.model.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameObjectRepository extends JpaRepository<GameObject, Integer> {
    Optional<GameObject> findGameObjectById(Integer id);
}
