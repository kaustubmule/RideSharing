package com.example.user.service.exception;

import com.example.user.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        ApiResponse response = ApiResponse.builder().message(message).success(true).httpStatus(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
//        ApiResponse response = ApiResponse.builder()
//                .message(message)
//                .success(false) // Set success to false for exceptions
//                .httpStatus(HttpStatus.NOT_FOUND)
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}