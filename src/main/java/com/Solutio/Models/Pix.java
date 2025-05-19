package com.Solutio.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Pix {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "charge_id")
    private Charge charge;

    private String qrCode;
    private String pixKey;
    private String txid;
    private String qrCodeUrl;
}

