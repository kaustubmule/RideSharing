package com.example.service;

import com.example.entity.Payment;

import java.util.List;

public interface PaymentService {

    //create
    Payment create(Payment payment);

    //get all payments
    List<Payment> getPayments();

    //get all payments by userid
    List<Payment> getPaymentByUserId(String userId);

    //get all payments by ride
    List<Payment> getPaymentByRideId(String rideId);

    //update payment status
    Payment updatePaymentStatus(String paymentId, String status);

}
