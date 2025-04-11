package com.example.user.service.client;

import com.example.user.service.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentServiceClient {
    @GetMapping("/payments/users/{userId}")
    List<Payment> getPaymentsByUser(@PathVariable("userId") String userId);
}