package com.example.leverxfinalproject.controller;

import com.example.leverxfinalproject.dto.request.GameObjectRequest;
import com.example.leverxfinalproject.dto.response.GameObjectResponse;
import com.example.leverxfinalproject.service.GameObjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameObjectController {
    private final GameObjectService gameObjectService;

    @GetMapping("/objects")
    public ResponseEntity<List<GameObjectResponse>> findAll() {
        return ResponseEntity.ok(gameObjectService.findAll());
    }

    @PostMapping("/objects")
    public ResponseEntity<GameObjectResponse> save(
            @Valid @RequestBody GameObjectRequest request,
            Principal principal) {
        return ResponseEntity.ok(gameObjectService.save(request, principal.getName()));
    }

    @PutMapping("/objects/{objectId}")
    public ResponseEntity<GameObjectResponse> update(
            @PathVariable Integer objectId,
            @Valid @RequestBody GameObjectRequest request,
            Principal principal) {
        return ResponseEntity.ok(gameObjectService.update(request, objectId, principal.getName()));
    }

    @DeleteMapping("/objects/{objectId}")
    public void delete(
            @PathVariable Integer objectId,
            Principal principal) {
        gameObjectService.delete(objectId, principal.getName());
    }


}
