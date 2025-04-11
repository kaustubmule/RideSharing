package com.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String paymentId;
    private String userId;
    private String rideId;
    private String paymentStatus;
    private Date paymentDate;
    private double paymentAmount;

}
