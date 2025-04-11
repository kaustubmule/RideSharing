package com.example.service.impl;

import com.example.client.PaymentServiceClient;
import com.example.client.UserServiceClient;
import com.example.entity.Payment;
import com.example.entity.Ride;
import com.example.entity.RideStatus;
import com.example.entity.User;
import com.example.repository.RideRepo;
import com.example.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepo rideRepository;
    private final UserServiceClient userServiceClient;
    private final PaymentServiceClient paymentServiceClient; // Inject PaymentServiceClient - already present

    @Autowired
    public RideServiceImpl(RideRepo rideRepository, UserServiceClient userServiceClient,
                           PaymentServiceClient paymentServiceClient) {
        this.rideRepository = rideRepository;
        this.userServiceClient = userServiceClient;
        this.paymentServiceClient = paymentServiceClient;
    }

    // Fetch drivers by role and check if they are free (i.e., not assigned to any ongoing ride)
    private User assignDriver() {
        // First, find all pending rides grouped by pickup and drop locations
        Map<String, List<Ride>> locationGroups = rideRepository
                .findByStatus(RideStatus.PENDING_DRIVER_ASSIGNMENT)
                .stream()
                .collect(Collectors.groupingBy(
                        ride -> ride.getPickupLocation() + "|" + ride.getDropLocation()));

        // Check if we have any group with exactly 3 passengers
        for (Map.Entry<String, List<Ride>> entry : locationGroups.entrySet()) {
            if (entry.getValue().size() == 3) {

                List<User> availableDrivers = userServiceClient.getAllDrivers();

                for (User driver : availableDrivers) {
                    // Check if driver is not already assigned to any ongoing ride
                    boolean isAssignedToRide = rideRepository.existsByDriverIdAndStatusNot(
                            driver.getUserId(),
                            RideStatus.COMPLETED);

                    if (!isAssignedToRide) {
                        // Assign this driver to all 3 rides
                        for (Ride ride : entry.getValue()) {
                            ride.setDriverId(driver.getUserId());
                            ride.setStatus(RideStatus.DRIVER_ASSIGNED);
                            rideRepository.save(ride);
                        }
                        return driver;
                    }
                }
            }
        }
        // No group of 3 passengers found / no available drivers
        return null;
    }

    @Scheduled(fixedRate = 10000) // This runs every minute (10000 ms)
    public void assignDriverToPendingRides() {
        List<Ride> pendingRides = rideRepository.findByStatus(RideStatus.PENDING_DRIVER_ASSIGNMENT);

        for (Ride ride : pendingRides) {
            // Check if there is a free driver available
            User assignedDriver = assignDriver();

            if (assignedDriver != null) {
                // Assign the driver to the ride and update the ride's status
                ride.setDriverId(assignedDriver.getUserId());
                ride.setStatus(RideStatus.DRIVER_ASSIGNED);
                rideRepository.save(ride); // Save the updated ride
            }
        }
    }

    @Override
    public Ride createRide(Ride ride) {
        String rideId = UUID.randomUUID().toString();
        ride.setRideId(rideId);
        ride.setStatus(RideStatus.PENDING_DRIVER_ASSIGNMENT);
        Ride createdRide = rideRepository.save(ride);

        User assignedDriver = assignDriver();

        if (assignedDriver != null) {
            // Driver was assigned (which means we had exactly 3 matching rides)
            createdRide.setDriverId(assignedDriver.getUserId());
            createdRide.setStatus(RideStatus.DRIVER_ASSIGNED);
            rideRepository.save(createdRide);
        }
        return createdRide;
    }

    @Override
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    @Override
    public Ride getRideById(String rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found with id: " + rideId));
    }

    @Override
    public Ride updateRide(String rideId, Ride ride) {
        Ride existingRide = getRideById(rideId);
        existingRide.setPickupLocation(ride.getPickupLocation());
        existingRide.setDropLocation(ride.getDropLocation());
        return rideRepository.save(existingRide);
    }

    @Override
    public void deleteRide(String rideId) {
        rideRepository.deleteById(rideId);
    }

    @Override
    public double calculateFare(Ride ride) {
        double baseFare = 50.0;
        double perKmCharge = 10.0;
        return baseFare + (perKmCharge * ride.getDistance());
    }

    @Override
    public Payment createPaymentForRide(Ride ride, double fare) {
        Payment payment = new Payment();
        payment.setRideId(ride.getRideId());
        payment.setUserId(ride.getUserId());
        payment.setPaymentAmount(fare);

        // Call the Payment Service via Feign
        return paymentServiceClient.createPayment(payment);
    }

    @Override
    public Ride updateRideStatus(String rideId, RideStatus status) {
        // Fetch the ride by ID
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found with id: " + rideId));

        // Set the new status
        try {
            RideStatus rideStatus = RideStatus.valueOf(String.valueOf(status)); // Convert status to RideStatus enum
            ride.setStatus(rideStatus);
            return rideRepository.save(ride); // Save the updated ride
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status provided: " + status);
        }
    }


}