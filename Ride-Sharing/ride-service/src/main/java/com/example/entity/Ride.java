package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;

    private Long userId;
    private Long driverId;
    private String pickupLoc;
    private String dropoffLoc;
    private double distance;
    private String status;  // "REQUESTED", "CONFIRMED", "COMPLETED"

    public Ride(Long rideId, String status, double distance, String dropoffLoc, String pickupLoc, Long driverId, Long userId) {
        this.rideId = rideId;
        this.status = status;
        this.distance = distance;
        this.dropoffLoc = dropoffLoc;
        this.pickupLoc = pickupLoc;
        this.driverId = driverId;
        this.userId = userId;
    }

    public Ride() {
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDropoffLoc() {
        return dropoffLoc;
    }

    public void setDropoffLoc(String dropoffLoc) {
        this.dropoffLoc = dropoffLoc;
    }

    public String getPickupLoc() {
        return pickupLoc;
    }

    public void setPickupLoc(String pickupLoc) {
        this.pickupLoc = pickupLoc;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}