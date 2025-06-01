package com.Solutio.Repositories;

import com.Solutio.Models.Charge;
import com.Solutio.Models.Customer;
import com.Solutio.Models.Enums.ChargeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChargeRepository extends JpaRepository<Charge, UUID> {

    List<Charge> findByCustomer(Customer customer);

    List<Charge> findByStatus(ChargeStatus chargeStatus);

    List<Charge> findByCustomerAndStatus(Customer customer, ChargeStatus status);

    Charge findByExternalId(String externalId);
}
