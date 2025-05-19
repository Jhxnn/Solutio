package com.Solutio.Models;

import com.Solutio.Models.Enums.ChargeStatus;
import com.Solutio.Models.Enums.ChargeType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private BigDecimal amount;
    private String description;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    private ChargeStatus status;

    @Enumerated(EnumType.STRING)
    private ChargeType type;
}