package com.example.dto;

public class RideRequest {
    private Long userId;
    private String pickupLoc;
    private String dropoffLoc;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}