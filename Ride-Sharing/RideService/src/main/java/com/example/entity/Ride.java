package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rides")
public class Ride {
    @Id
    @Column(name = "rideId", length = 50)
    private String rideId;
    private String userId;
    private String paymentId;
    private String pickupLocation;
    private String dropLocation;
    private String driverId;
    private double distance;
    @Enumerated(EnumType.STRING)
    private RideStatus status;
}