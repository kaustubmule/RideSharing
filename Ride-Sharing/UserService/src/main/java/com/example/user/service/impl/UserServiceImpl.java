package com.example.user.service.impl;

import com.example.user.client.RideServiceClient;
import com.example.user.client.PaymentServiceClient;
import com.example.user.entity.Payment;
import com.example.user.entity.Ride;
import com.example.user.entity.User;
import com.example.user.entity.UserPrincipal;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.repository.UserRepository;
import com.example.user.service.JWTService;
import com.example.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RideServiceClient rideService;
    private final PaymentServiceClient paymentService;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RideServiceClient rideService,
            PaymentServiceClient paymentService) {
        this.userRepository = userRepository;
        this.rideService = rideService;
        this.paymentService = paymentService;
    }

    @Override
    public User registerUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public String verify(User user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }


    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given ID not found on server"));

        // Fetch payments using Feign
        List<Payment> payments = paymentService.getPaymentsByUser(user.getUserId());
        logger.info("Payments fetched for user {}: {}", userId, payments);

        // Fetch rides for each payment and set it
        List<Payment> paymentList = payments.stream().map(payment -> {
            Ride ride = rideService.getRide(payment.getRideId());
            payment.setRide(ride);
            return payment;
        }).collect(Collectors.toList());

        user.setPayments(paymentList);
        return user;
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(String userId, User user) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("Update failed: User not found with ID {}", userId);
                    return new ResourceNotFoundException("User not found with ID: " + userId);
                });

        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            existingUser.setUsername(user.getUsername());
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            existingUser.setEmail(user.getEmail());
        }

        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            existingUser.setPhone(user.getPhone());
        }

        if (user.getRole() != null && !user.getRole().isEmpty()) {
            existingUser.setRole(user.getRole());
        }

        return userRepository.save(existingUser);
    }


    @Override
    public List<User> getAllUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }




}
