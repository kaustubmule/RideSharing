package com.example.client;

import com.example.dto.RideRequest;
import com.example.dto.RideResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ride-service", url = "http://localhost:8082")
public interface RideClient {
    @PostMapping("/api/rides/book")
    RideResponse bookRide(@RequestBody RideRequest rideRequest);
}