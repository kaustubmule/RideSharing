package com.example.controller;

import com.example.dto.RideRequest;
import com.example.entity.Ride;
import com.example.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/book")
    public ResponseEntity<Ride> bookRide(@RequestBody RideRequest rideRequest) {
        return ResponseEntity.ok(rideService.bookRide(rideRequest));
    }
}