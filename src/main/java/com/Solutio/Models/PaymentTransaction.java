package com.Solutio.Models;

import com.Solutio.Models.Enums.ChargeType;
import com.Solutio.Models.Enums.TransactionStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "charge_id")
    private Charge charge;

    @Enumerated(EnumType.STRING)
    private ChargeType method;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private String externalReference;
}