package com.example.controller;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")  // Base path
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")  // API endpoint
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.authenticateUser(request);

        if (response.getRole() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
