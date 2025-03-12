package com.example.leverxfinalproject.service;


import com.example.leverxfinalproject.dto.request.GameObjectRequest;
import com.example.leverxfinalproject.dto.response.GameObjectResponse;
import com.example.leverxfinalproject.enums.Role;
import com.example.leverxfinalproject.model.GameObject;
import com.example.leverxfinalproject.model.User;
import com.example.leverxfinalproject.repository.GameObjectRepository;
import com.example.leverxfinalproject.repository.SellerProfileRepository;
import com.example.leverxfinalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameObjectService {
    private final GameObjectRepository gameObjectRepository;
    private final UserRepository userRepository;
    private final SellerProfileRepository sellerProfileRepository;


    public List<GameObjectResponse> findAll() {
        return gameObjectRepository
                .findAll()
                .stream()
                .map(GameObjectResponse::from)
                .toList();
    }

    @Transactional
    public GameObjectResponse save(GameObjectRequest request, String email) {
        User user = userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("wrong username"));
        if(user.getRole() != Role.SELLER) {
            throw new IllegalArgumentException("you have to be SELLER");
        }
        if(!sellerProfileRepository.existsSellerProfileByApprovedAndUser_Id(true, user.getId())) {
            throw new IllegalArgumentException("this user does not have active seller profile");
        }

        GameObject gameObject = new GameObject(
                request.title(),
                request.text(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                user
        );

        gameObjectRepository.save(gameObject);

        return GameObjectResponse.from(gameObject);
    }

    @Transactional
    public GameObjectResponse update(GameObjectRequest request, Integer objectId, String email) {
        User user = userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("wrong username"));

        GameObject gameObject = gameObjectRepository
                .findGameObjectById(objectId)
                .orElseThrow(() -> new IllegalArgumentException("wrong object id"));

        if(!gameObject.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("cant manage other users game objects");
        }

        gameObject.setUpdatedAt(LocalDateTime.now());
        gameObject.setTitle(request.title());
        gameObject.setText(request.text());
        gameObjectRepository.save(gameObject);

        return GameObjectResponse.from(gameObject);
    }


    @Transactional
    public void delete(Integer objectId, String email) {
        User user = userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("wrong username"));

        GameObject gameObject = gameObjectRepository
                .findGameObjectById(objectId)
                .orElseThrow(() -> new IllegalArgumentException("wrong object id"));
        if(!gameObject.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("cant manage other users game objects");
        }

        gameObjectRepository.delete(gameObject);
    }
}
