package com.example.leverxfinalproject.service;


import com.example.leverxfinalproject.dto.request.*;
import com.example.leverxfinalproject.dto.response.UserResponse;
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

        return UserResponse.from(user);
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

        return UserResponse.from(user);
    }

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

    public String forgotPassword(ForgotPasswordRequest request) {
        if(!userRepository.existsUserByEmail(request.email())) {
            throw new UsernameNotFoundException("user with this email does not exist");
        }
        String code = generateRandomCode();
        verificationCodeService.save(VerificationCodeType.CHANGE_PASSWORD, request.email(), code);
        mailSenderService.sendMail(request.email(), "Change Password", "Your Verification code: " + code);

        return "email has been sent";
    }

    @Transactional
    public UserResponse resetPassword(ResetPasswordRequest request) {
        String code = verificationCodeService
                .get(VerificationCodeType.CHANGE_PASSWORD, request.email())
                .orElseThrow(() -> new VerificationException("wrong email"));
        if(!code.equals(request.code())) {
            throw new VerificationException("wrong code");
        }

        User user = userRepository
                .findUserByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("wrong email"));
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        return UserResponse.from(user);
    }

    public boolean checkCode(String email, String code) {
        String codeFromRedis = verificationCodeService
                .get(VerificationCodeType.CHANGE_PASSWORD, email)
                .orElseThrow(() -> new VerificationException("wrong email"));
        return code.equals(codeFromRedis);
    }


    private String generateRandomCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

}
