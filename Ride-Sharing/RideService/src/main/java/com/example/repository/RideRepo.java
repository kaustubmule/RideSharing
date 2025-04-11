package com.example.repository;


import com.example.entity.Ride;
import com.example.entity.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepo extends JpaRepository<Ride, String> {
    // Custom query method to check if a driver is assigned to any ride that's not completed
    boolean existsByDriverIdAndStatusNot(String driverId, RideStatus status);

    List<Ride> findByStatus(RideStatus rideStatus);
}