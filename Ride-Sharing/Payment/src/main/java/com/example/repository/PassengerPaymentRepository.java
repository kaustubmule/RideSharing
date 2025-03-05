package com.example.repository;

import com.example.entity.PassengerPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerPaymentRepository extends JpaRepository<PassengerPayment, Long> {
    List<PassengerPayment> findByPaymentId(Long paymentId);
}