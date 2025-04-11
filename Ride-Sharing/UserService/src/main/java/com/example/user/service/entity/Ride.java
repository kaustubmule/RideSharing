package com.example.user.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ride {
    private String rideId;
    private String paymentId;
    private String pickupLocation;
    private String dropLocation;
    private String driverId;
    private double distance;
    @Enumerated(EnumType.STRING)
    private RideStatus status;
}
