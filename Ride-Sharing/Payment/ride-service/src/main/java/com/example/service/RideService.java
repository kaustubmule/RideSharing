package com.example.service;

import com.example.client.DriverClient;
import com.example.client.UserClient;
import com.example.dto.DriverResponse;
import com.example.dto.RideRequest;
import com.example.entity.Ride;
import com.example.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final UserClient userClient;
    private final DriverClient driverClient;

    public RideService(RideRepository rideRepository, UserClient userClient, DriverClient driverClient) {
        this.rideRepository = rideRepository;
        this.userClient = userClient;
        this.driverClient = driverClient;
    }

    public Ride bookRide(RideRequest rideRequest) {
        // Check if user exists
        var userResponse = userClient.getUserById(rideRequest.getUserId());

        if (userResponse == null) {
            throw new RuntimeException("User not found");
        }

        // Find available driver
//        var driverResponse = driverClient.getAvailableDriver();
//        if (driverResponse == null) {
//            throw new RuntimeException("No available drivers");
//        }

        var driverResponse = new DriverResponse();
        driverResponse.setDriverId(1L);
        driverResponse.setVehicleNumber("DUMMY-1234");
        driverResponse.setRating(5.0);

        // Create ride entity
        Ride ride = new Ride();
        ride.setUserId(rideRequest.getUserId());
        ride.setDriverId(driverResponse.getDriverId());
        ride.setPickupLoc(rideRequest.getPickupLoc());
        ride.setDropoffLoc(rideRequest.getDropoffLoc());
        ride.setDistance(10.5); // Dummy distance
        ride.setStatus("CONFIRMED");

        return rideRepository.save(ride);
    }
}