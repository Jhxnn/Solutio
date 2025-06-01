package com.Solutio.Repositories;

import com.Solutio.Models.Customer;
import com.Solutio.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Customer findByEmail(String email);

    Customer findByCpfCnpj(String cpfCnpj);

    boolean existsByEmail(String email);

    boolean existsByCpfCnpj(String cpfCnpj);

    List<Customer> findByUser(User user);

}
