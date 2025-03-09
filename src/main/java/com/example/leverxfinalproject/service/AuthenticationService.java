package com.example.leverxfinalproject.service;


import com.example.leverxfinalproject.dto.LoginRequest;
import com.example.leverxfinalproject.dto.RegistrationRequest;
import com.example.leverxfinalproject.dto.UserResponse;
import com.example.leverxfinalproject.dto.VerifyRegistrationRequest;
import com.example.leverxfinalproject.enums.Role;
import com.example.leverxfinalproject.enums.VerificationCodeType;
import com.example.leverxfinalproject.exception.VerificationException;
import com.example.leverxfinalproject.model.User;
import com.example.leverxfinalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final VerificationCodeService verificationCodeService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public UserResponse register(RegistrationRequest request) {
        if(userRepository.existsUserByEmail(request.email())) {
            throw new VerificationException("user with this email already exists");
        }

        User user = new User(
                request.firstName(),
                request.lastName(),
                passwordEncoder.encode(request.password()),
                request.email(),
                LocalDateTime.now(),
                Role.SELLER,
                false,
                false
        );
        userRepository.save(user);

        String code = generateRandomCode();
        verificationCodeService.save(VerificationCodeType.REGISTRATION, user.getEmail(), code);

        mailSenderService.sendMail(
                user.getEmail(),
                "Verification Code",
                "Your Verification code: " + code
        );

        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getRole().name());
    }

    @Transactional
    public UserResponse verifyCode(VerifyRegistrationRequest request) {
        User user = userRepository.findUserByEmail(request.email()).orElseThrow(() -> new UsernameNotFoundException("wrong email"));
        if(user.isEnabled()) {
            throw new VerificationException("user is already verified");
        }

        String code = verificationCodeService
                .get(VerificationCodeType.REGISTRATION, request.email())
                .orElseThrow(() -> new VerificationException("login again to get new code"));


        if(!request.code().equals(code)) {
            throw new VerificationException("wrong code");
        }

        user.setEnabled(true);
        userRepository.save(user);
        verificationCodeService.delete(VerificationCodeType.REGISTRATION, user.getEmail());

        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getRole().name());
    }

    @Transactional
    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        User user = userRepository
                .findUserByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("wrong email"));

        if(!user.isEnabled()) {
            if(verificationCodeService.get(VerificationCodeType.REGISTRATION, user.getEmail()).isEmpty()) {
                String code = generateRandomCode();
                verificationCodeService.save(VerificationCodeType.REGISTRATION, user.getEmail(), code);

                mailSenderService.sendMail(
                        user.getEmail(),
                        "Verification Code",
                        "Your Verification code: " + code
                );
                throw new VerificationException("user not verified, code has been sent again");
            }
            throw new VerificationException("user not verified");
        }

        return jwtService.generateToken(user);
    }

    private String generateRandomCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
