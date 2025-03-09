package com.example.leverxfinalproject.service;

import com.example.leverxfinalproject.enums.VerificationCodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {

    private final RedisTemplate<String, String> redisTemplate;

    public void save(VerificationCodeType type, String email, String code) {

        redisTemplate.opsForValue().set(type.name() + ":" + email, code, getDuration(type));
    }

    public Optional<String> get(VerificationCodeType type, String email) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(type.name() + ":" + email));
    }

    public void delete(VerificationCodeType type, String email) {
        redisTemplate.delete(type.name() + ":" + email);
    }

    private Duration getDuration(VerificationCodeType type) {
        return switch (type) {
            case REGISTRATION -> Duration.ofDays(1L);
            case CHANGE_PASSWORD -> Duration.ofMinutes(15L);
            default -> Duration.ZERO;
        };
    }
}
