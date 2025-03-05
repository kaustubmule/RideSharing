package com.example.client;

import com.example.dto.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "driver-service", url = "http://localhost:8083/api/drivers")
public interface DriverClient {

    @GetMapping("/available")
    DriverResponse getAvailableDriver();
}