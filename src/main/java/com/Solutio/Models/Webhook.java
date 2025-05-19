package com.Solutio.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Webhook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String event;

    @Lob
    private String payload;

    private LocalDateTime receivedAt;
    private boolean processed;
}
