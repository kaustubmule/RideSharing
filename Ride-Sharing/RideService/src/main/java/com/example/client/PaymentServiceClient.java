package com.example.client;

import com.example.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentServiceClient {

    @GetMapping("/payments/{paymentId}")
    Payment getPayment(@PathVariable("paymentId") String paymentId);

    @PutMapping("/payments/{paymentId}/status") // New endpoint to update payment status
    Payment updatePaymentStatus(@PathVariable("paymentId") String paymentId, @RequestParam("status") String status);

    @PostMapping("/payments")
    Payment createPayment(Payment payment);
}