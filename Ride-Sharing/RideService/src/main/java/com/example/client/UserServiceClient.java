package com.example.client;


import com.example.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/users/{userId}")
    User getUser(@PathVariable("userId") String userId);

    @GetMapping("/users")
    List<User> getAllUsers(); // You might need to filter for drivers on the UserService side for efficiency

    @GetMapping("/users/role/driver") //New endpoint to get drivers
    List<User> getAllDrivers();
}