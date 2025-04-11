package com.example.user.service.client;


import com.example.user.service.entity.Ride;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "RIDE-SERVICE")
public interface RideServiceClient {
    @GetMapping("/rides/{rideId}")
    public Ride getRide(@PathVariable("rideId") String rideId);

    //create ride
    @PostMapping("/rides")
    public Ride createRide(Ride ride);

    //update / modify ride
    @PutMapping("/rides/{rideId}")
    public Ride updateRide(@PathVariable("rideId") String rideId, Ride ride);

    //delete ride
    @DeleteMapping("/rides/{rideId}")
    public Ride deleteRide(@PathVariable("rideId") String ratingId);
}
