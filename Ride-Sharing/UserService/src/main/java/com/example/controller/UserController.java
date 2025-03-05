package com.example.controller;

import com.example.client.DriverClient;
import com.example.client.RideClient;
import com.example.dto.DriverResponse;
import com.example.dto.RideRequest;
import com.example.dto.RideResponse;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RideClient rideClient;
    private final DriverClient driverClient;

    @Autowired
    public UserController(UserService userService, RideClient rideClient, DriverClient driverClient) {
        this.userService = userService;
        this.rideClient = rideClient;
        this.driverClient = driverClient;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation error: " + e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/role")
    public ResponseEntity<String> getUserRole(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user.getRole());
    }

    @PostMapping("/{id}/book-ride")
    public ResponseEntity<RideResponse> bookRide(@PathVariable Long id, @RequestBody RideRequest rideRequest) {
        rideRequest.setUserId(id);
        return ResponseEntity.ok(rideClient.bookRide(rideRequest));
    }

    @GetMapping("/{id}/driver")
    public ResponseEntity<DriverResponse> getAssignedDriver(@PathVariable Long id) {
        return ResponseEntity.ok(driverClient.getAssignedDriver(id));
    }
}


class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
