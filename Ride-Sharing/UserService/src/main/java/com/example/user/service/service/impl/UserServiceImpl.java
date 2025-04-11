package com.example.user.service.service.impl;

import com.example.user.service.client.RideServiceClient;
import com.example.user.service.client.PaymentServiceClient;
import com.example.user.service.entity.Payment;
import com.example.user.service.entity.Ride;
import com.example.user.service.entity.User;
import com.example.user.service.exception.ResourceNotFoundException;
import com.example.user.service.repository.UserRepository;
import com.example.user.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserServiceImpl(UserRepository userRepository, RideServiceClient rideService, PaymentServiceClient paymentService) {
        this.userRepository = userRepository;
        this.rideService = rideService;
        this.paymentService = paymentService;
    }

    @Override
    public User saveUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given ID not found on server"));

        // Fetch payments using Feign instead of RestTemplate
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
        return null;
    }

    @Override
    public List<User> getAllDriversByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
