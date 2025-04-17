package com.example.user.controller;

import com.example.user.entity.User;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs for managing users and their roles")
public class UserController {

        @Autowired
        private UserService userService;

        private final Logger logger = LoggerFactory.getLogger(UserController.class);

        @PostMapping("/register")
        @Operation(summary = "Register new user", description = "Registers a new user with the provided details")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "User registered successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid input")
        })
        public ResponseEntity<User> createUser(@RequestBody User user) {
                User createdUser = userService.registerUser(user);
                return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }


        @PostMapping("/login")
        @Operation(summary = "Login user", description = "Authenticates user with username and password")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Login successful"),
                @ApiResponse(responseCode = "401", description = "Invalid credentials")
        })
        public String loginUser(@RequestBody User user) {
                return userService.verify(user);
        }


        @GetMapping("/{userId}")
        @Operation(summary = "Get user by ID", description = "Retrieves user details based on user ID")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "User found"),
                @ApiResponse(responseCode = "404", description = "User not found")
        })
        @CircuitBreaker(name = "paymentRideBreaker", fallbackMethod = "paymentRideFallback")
        @RateLimiter(name = "userServiceRateLimiter", fallbackMethod = "rateLimiterFallback")
        public ResponseEntity<User> getUserById(
                @Parameter(description = "ID of the user to be retrieved", required = true)
                @PathVariable String userId) {

                logger.info("Get user by ID: {}", userId);
                User user = userService.getUser(userId);

                if (user == null) {
                        throw new ResourceNotFoundException("User with ID " + userId + " not found");
                }

                return ResponseEntity.ok(user);
        }

        @GetMapping
        @Operation(summary = "Get all users", description = "Retrieves a list of all registered users")
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user list")
        public ResponseEntity<List<User>> getAllUser() {
                List<User> allUsers = userService.getAllUser();
                return ResponseEntity.ok(allUsers);
        }


        @PutMapping("/{userId}")
        @Operation(summary = "Update user details", description = "Updates existing user information based on user ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "User updated successfully"),
                        @ApiResponse(responseCode = "404", description = "User not found")
        })
        public ResponseEntity<User> updateUser(
                        @Parameter(description = "ID of the user to be updated", required = true) @PathVariable String userId,
                        @RequestBody User user) {
                User updatedUser = userService.updateUser(userId, user);
                logger.info("User updated successfully: {}", userId);
                return ResponseEntity.ok(updatedUser);
        }

        @GetMapping("/role/{role}")
        @Operation(summary = "Get users by role", description = "Retrieves a list of users by role")
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user list")
        public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
                List<User> users = userService.getAllUsersByRole(role.toLowerCase());
                return ResponseEntity.ok(users);
        }


        @GetMapping("/search")
        public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
                User user = userService.findByUsername(username);
                if (user != null) {
                        return ResponseEntity.ok(user);
                }
                return ResponseEntity.notFound().build();
        }


        // Fallback methods
        public ResponseEntity<User> paymentRideFallback(String userId, Exception ex) {
                logger.info("Fallback executed: Service unavailable - {}", ex.getMessage());
                return ResponseEntity.ok(User.builder()
                                .email("dummy@gmail.com")
                                .username("dummy")
                                .userId("1234")
                                .build());
        }

        public ResponseEntity<String> rateLimiterFallback(Exception ex) {
                logger.warn("Rate limit exceeded: {}", ex.getMessage());
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                                .body("Too many requests - Please try again later.");
        }
}