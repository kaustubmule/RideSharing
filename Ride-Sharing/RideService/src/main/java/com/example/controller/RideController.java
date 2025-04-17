package com.example.controller;

import com.example.entity.Payment;
import com.example.entity.Ride;
import com.example.entity.RideStatus;
import com.example.service.RideService;
import com.example.client.PaymentServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rides")
public class RideController {
    private final RideService rideService;
    private final PaymentServiceClient paymentServiceClient;

    @Autowired
    public RideController(RideService rideService, PaymentServiceClient paymentServiceClient) {
        this.rideService = rideService;
        this.paymentServiceClient = paymentServiceClient;
    }

    @PostMapping
    public ResponseEntity<Ride> createRide(@RequestBody Ride ride) {

        Ride createdRide = rideService.createRide(ride);


        double fare = rideService.calculateFare(createdRide);


        Payment createdPayment = rideService.createPaymentForRide(createdRide, fare);


        if (createdPayment != null && createdPayment.getPaymentId() != null) {
            createdRide.setPaymentId(createdPayment.getPaymentId());
            Ride updatedRideWithPayment = rideService.updateRide(createdRide.getRideId(), createdRide);
            return new ResponseEntity<>(updatedRideWithPayment, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(createdRide, HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<List<Ride>> getAllRides() {
        List<Ride> rides = rideService.getAllRides();
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<Ride> getRideById(@PathVariable String rideId) {
        Ride ride = rideService.getRideById(rideId);
        return new ResponseEntity<>(ride, HttpStatus.OK);
    }


    @PutMapping("/{rideId}/status")
    public ResponseEntity<Ride> updateRideStatus(
            @PathVariable String rideId,
            @RequestParam("status") RideStatus status // Status will be passed as a query parameter
    ) {
        // Update the status of the ride
        Ride updatedRide = rideService.updateRideStatus(rideId, status);

        if (updatedRide != null) {
            return ResponseEntity.ok(updatedRide);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{rideId}")
    public ResponseEntity<?> updateRide(@PathVariable String rideId, @RequestBody Ride ride) {
        try {
            System.out.println("Updating ride with ID: " + rideId);
            System.out.println("Request body: " + ride);
            Ride updatedRide = rideService.updateRide(rideId, ride);
            return new ResponseEntity<>(updatedRide, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error updating ride: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{rideId}")
    public ResponseEntity<Void> deleteRide(@PathVariable String rideId) {
        rideService.deleteRide(rideId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}