package com.example.controller;

import com.example.dto.CreatePaymentRequest;
import com.example.entity.Payment;
import com.example.entity.PassengerPayment;
import com.example.service.PaymentService;
import com.example.service.PassengerPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments") // Existing endpoint for payments
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PassengerPaymentService passengerPaymentService; // New service for passenger payments

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody CreatePaymentRequest request) {
        Payment payment = paymentService.createPayment(
                String.valueOf(request.getRideId()),
                String.valueOf(request.getDriverId()),
                request.getPassengerPayments(),
                request.getCurrency()
        );
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{paymentId}/passenger-payments")
    public ResponseEntity<List<PassengerPayment>> getPassengerPayments(@PathVariable Long paymentId) {
        List<PassengerPayment> passengerPayments = passengerPaymentService.getPassengerPayments(paymentId);
        return ResponseEntity.ok(passengerPayments);
    }
}