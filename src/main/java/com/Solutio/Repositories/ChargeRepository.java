package com.Solutio.Repositories;

import com.Solutio.Models.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChargeRepository extends JpaRepository<Charge, UUID> {
}
