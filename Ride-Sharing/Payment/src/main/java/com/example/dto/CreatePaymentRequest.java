package com.example.dto;

import com.example.entity.PassengerPayment;
import java.math.BigDecimal;
import java.util.List;

public class CreatePaymentRequest {
    private Long rideId;
    private Long driverId;
    private List<PassengerPayment> passengerPayments;
    private String currency;

    // Getters and Setters
    public Long getRideId() { return rideId; }
    public void setRideId(Long rideId) { this.rideId = rideId; }
    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
    public List<PassengerPayment> getPassengerPayments() { return passengerPayments; }
    public void setPassengerPayments(List<PassengerPayment> passengerPayments) { this.passengerPayments = passengerPayments; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public void setTotalAmount(BigDecimal bigDecimal) {

    }
}