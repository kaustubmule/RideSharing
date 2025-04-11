package com.example.user.service.controller;

import com.example.user.service.entity.User;
import com.example.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);


    //create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //get user by id
    @GetMapping("/{userId}")
    @RateLimiter(name="userRateLimiter",fallbackMethod = "paymentRideFallback")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        logger.info("Retry attempt for userId: {}", userId);
        User user = userService.getUser(userId);
        if (user != null) { // Handle case where user is not found, though getUser service should throw exception
            return ResponseEntity.ok(user); // Use ResponseEntity.ok() for success
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found (though exception handler should manage this)
        }
    }


    public ResponseEntity<User>  paymentRideFallback(String userId, Exception ex) {
        logger.info("Fallback is executed because service is down: {}", ex.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com")
                .username("dummy")
                .userId("1234")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    //get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }

    // Get all drivers
    @GetMapping("/role/driver")
    public ResponseEntity<List<User>> getAllDrivers() {
        List<User> drivers = userService.getAllDriversByRole("driver");
        return ResponseEntity.ok(drivers);
    }

}
