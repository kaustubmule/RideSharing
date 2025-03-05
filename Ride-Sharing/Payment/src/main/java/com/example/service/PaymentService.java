package com.example.service;

import com.example.entity.Payment;
import com.example.entity.PassengerPayment;
import com.example.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPayment(String rideId, String driverId, List<PassengerPayment> passengerPayments,
            String currency) {
        Payment payment = new Payment();
        payment.setRideId(rideId);
        payment.setDriverId(driverId);
        payment.setCurrency(currency);
        payment.setCreatedAt(new Date());

        // Set up bidirectional relationship
        payment.setPassengerPayments(passengerPayments);
        for (PassengerPayment pp : passengerPayments) {
            pp.setPayment(payment);
        }

        // Calculate total amount
        BigDecimal totalAmount = passengerPayments.stream()
                .map(PassengerPayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        payment.setTotalAmount(totalAmount);

        return paymentRepository.save(payment);
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }
}
