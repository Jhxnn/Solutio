package com.Solutio.Repositories;

import com.Solutio.Models.Boleto;
import com.Solutio.Models.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoletoRepository extends JpaRepository<Boleto, UUID> {


    Boleto findByCharge(Charge charge);

}
