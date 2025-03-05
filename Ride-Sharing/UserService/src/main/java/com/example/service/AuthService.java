package com.example.service;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginResponse authenticateUser(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        System.out.println("Login attempt for email: " + request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found, stored password hash: " + user.getPassword()); //remove ( only for debugging )
            System.out.println("Attempting to match with provided password");

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return new LoginResponse("Login successful", user.getRole());
            } else {
                System.out.println("Password match failed");
                return new LoginResponse("Invalid credentials", null);
            }
        } else {
            System.out.println("User not found with email: " + request.getEmail());
            return new LoginResponse("User not found", null);
        }
    }
}
