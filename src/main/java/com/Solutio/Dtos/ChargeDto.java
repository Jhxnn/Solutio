package com.Solutio.Dtos;

import com.Solutio.Models.Customer;
import com.Solutio.Models.Enums.ChargeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ChargeDto(
        UUID customer,
        BigDecimal amount,
        String description,
        ChargeType chargeType,
        LocalDate dueDate) {
}
