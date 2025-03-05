package com.example.dto;

import java.math.BigDecimal;

public class PassengerPaymentRequest {
    private Long userId; // Reference to User ID
    private BigDecimal amount;

    // Getters/setters

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}