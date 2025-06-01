package com.Solutio.Controllers;

import com.Solutio.Dtos.CustomerDto;
import com.Solutio.Models.Customer;
import com.Solutio.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customerDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerDto));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Customer>> findByUser(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findByUser());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> findByEmail(@PathVariable(name = "email")String email){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findByEmail(email));
    }

    @GetMapping("/cpfCnpj/{cpfCnpj}")
    public ResponseEntity<Customer> findByCpfCnpj(@PathVariable(name = "cpfCnpj")String cpfCnpj){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findByCpfCnpj(cpfCnpj));
    }

}
