package com.example.client;

import com.example.dto.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "driver-service", url = "http://localhost:8083")
public interface DriverClient {
    @GetMapping("/api/drivers/user/{userId}")
    DriverResponse getAssignedDriver(@PathVariable Long userId);
}