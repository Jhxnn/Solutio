package com.Solutio.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "charge_id")
    private Charge charge;

    private String barcode;
    private String digitableLine;
    private String boletoUrl;
    private String ourNumber;
}