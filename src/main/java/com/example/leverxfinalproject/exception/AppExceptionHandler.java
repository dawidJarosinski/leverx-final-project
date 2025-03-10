package com.example.leverxfinalproject.exception;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(VerificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String verificationExceptionHandler(VerificationException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String usernameNotFoundExceptionHandler(UsernameNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String usernameNotFoundExceptionHandler(IllegalArgumentException ex) {
        return ex.getMessage();
    }
}
