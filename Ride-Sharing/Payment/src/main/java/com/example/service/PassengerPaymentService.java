package com.example.service;

import com.example.entity.PassengerPayment;
import com.example.entity.Payment;
import com.example.repository.PassengerPaymentRepository;
import com.example.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerPaymentService {

    @Autowired
    private PassengerPaymentRepository passengerPaymentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    // ✅ Method to add a passenger's payment
    public PassengerPayment addPassengerPayment(Long paymentId, String userId, BigDecimal amount) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isEmpty()) {
            throw new RuntimeException("Payment not found with ID: " + paymentId);
        }
        Payment payment = paymentOptional.get();

        PassengerPayment passengerPayment = new PassengerPayment();
        passengerPayment.setPayment(payment);
        passengerPayment.setUserId(userId);
        passengerPayment.setAmount(amount);

        return passengerPaymentRepository.save(passengerPayment);
    }

    // ✅ Method to get all passenger payments for a specific payment
    public List<PassengerPayment> getPassengerPayments(Long paymentId) {
        return passengerPaymentRepository.findByPaymentId(paymentId);
    }
}