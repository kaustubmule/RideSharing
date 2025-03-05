package com.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rideId;

    @Column(nullable = false)
    private String driverId;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Marks this as the "parent" side of the relationship
    private List<PassengerPayment> passengerPayments;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private String currency;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRideId() { return rideId; }
    public void setRideId(String rideId) { this.rideId = rideId; }
    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }
    public List<PassengerPayment> getPassengerPayments() { return passengerPayments; }
    public void setPassengerPayments(List<PassengerPayment> passengerPayments) { this.passengerPayments = passengerPayments; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}