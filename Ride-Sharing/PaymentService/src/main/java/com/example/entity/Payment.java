package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
public class Payment {
    @Id
    private String paymentId;
    private String userId;
    private String rideId;
    private PaymentStatus paymentStatus; // Now correctly using PaymentStatus enum
    private double paymentAmount;
}