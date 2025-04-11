package com.example.service.impl;

import com.example.entity.Payment;
import com.example.entity.PaymentStatus;
import com.example.repository.PaymentRepository;
import com.example.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.repository = paymentRepository;
    }

    @Override
    public Payment create(Payment payment) {
        payment.setPaymentStatus(PaymentStatus.PENDING); // Set default status to PENDING enum
        return repository.save(payment);
    }

    @Override
    public List<Payment> getPayments() {
        return repository.findAll();
    }

    @Override
    public List<Payment> getPaymentByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Payment> getPaymentByRideId(String rideId) {
        return repository.findByRideId(rideId);
    }

    @Override
    public Payment updatePaymentStatus(String paymentId, String status) {
        Optional<Payment> paymentOptional = repository.findById(paymentId);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            try {
                PaymentStatus paymentStatusEnum = PaymentStatus.valueOf(status.toUpperCase()); // Convert String to enum
                payment.setPaymentStatus(paymentStatusEnum);
                return repository.save(payment);
            } catch (IllegalArgumentException e) {
                // Handle invalid status string (e.g., log an error, throw an exception)
                System.err.println("Invalid Payment Status: " + status);
                return null; // Or throw an exception
            }
        }
        return null; // Or throw an exception if payment not found
    }
}
