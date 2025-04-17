package com.example.controller;

import com.example.entity.Payment;
import com.example.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment Management", description = "APIs for handling payment processing and transactions")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(
            summary = "Create new payment",
            description = "Initiate a new payment transaction"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment details")
    })
    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.create(payment));
    }

    @Operation(
            summary = "Get all payments",
            description = "Retrieve list of all payment transactions"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved payment list")
    @GetMapping
    public ResponseEntity<List<Payment>> getPayments() {
        return ResponseEntity.ok(paymentService.getPayments());
    }

    @Operation(
            summary = "Get payments by user ID",
            description = "Retrieve payment history for specific user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payments"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUserId(
            @Parameter(description = "ID of the user to retrieve payments for", required = true)
            @PathVariable String userId) {

        return ResponseEntity.ok(paymentService.getPaymentByUserId(userId));
    }

    @Operation(
            summary = "Get payments by ride ID",
            description = "Retrieve payment details for specific ride"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payments"),
            @ApiResponse(responseCode = "404", description = "Ride not found")
    })
    @GetMapping("/rides/{rideId}")
    public ResponseEntity<List<Payment>> getPaymentsByRideId(
            @Parameter(description = "ID of the ride to retrieve payments for", required = true)
            @PathVariable String rideId) {

        return ResponseEntity.ok(paymentService.getPaymentByRideId(rideId));
    }

    @Operation(
            summary = "Update payment status",
            description = "Update the status of a payment transaction (e.g., PENDING, COMPLETED, FAILED)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid status value")
    })
    @PutMapping("/{paymentId}/status")
    public ResponseEntity<Payment> updatePaymentStatus(
            @Parameter(description = "ID of the payment to update", required = true)
            @PathVariable String paymentId,

            @Parameter(
                    description = "New status for the payment",
                    required = true,
                    examples = {
                            @ExampleObject(name = "Completed", value = "COMPLETED"),
                            @ExampleObject(name = "Failed", value = "FAILED"),
                            @ExampleObject(name = "Pending", value = "PENDING")

                    }
            )
            @RequestParam("status") String status) {

        Payment updatedPayment = paymentService.updatePaymentStatus(paymentId, status);
        return updatedPayment != null ?
                ResponseEntity.ok(updatedPayment) :
                ResponseEntity.notFound().build();
    }
}