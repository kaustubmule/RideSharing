package com.example.service;

import com.example.entity.Payment;
import com.example.entity.Ride;
import com.example.entity.RideStatus;

import java.util.List;


public interface RideService {

    Ride createRide(Ride ride);

    List<Ride> getAllRides();

    Ride getRideById(String rideId);

    Ride updateRide(String rideId, Ride ride);

    void deleteRide(String rideId);

    double calculateFare(Ride ride);
    Payment createPaymentForRide(Ride ride, double fare);

    Ride updateRideStatus(String rideId, RideStatus status);
}