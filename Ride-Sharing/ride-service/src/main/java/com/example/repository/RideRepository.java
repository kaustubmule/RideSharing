package com.example.repository;

import com.example.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByUserId(Long userId);
}