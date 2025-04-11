package com.example.repository;

import com.example.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    //custom finder methods
    List<Payment> findByUserId(String userId);
    List<Payment> findByRideId(String rideId);
}
