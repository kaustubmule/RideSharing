package com.example.user.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
