package com.Solutio.Controllers;

import com.Solutio.Dtos.CustomerDto;
import com.Solutio.Models.Customer;
import com.Solutio.Services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Operation(description = "Cria um cliente ")
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customerDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerDto));
    }

    @Operation(description = "Lista todos os clientes")
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll());
    }
    @Operation(description = "Lista todos os clientes do usuario logado")
    @GetMapping("/user")
    public ResponseEntity<List<Customer>> findByUser(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findByUser());
    }
    @Operation(description = "Lista todos os clientes do usuario logado buscando pelo Email")
    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> findByEmail(@PathVariable(name = "email")String email){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findByEmail(email));
    }

    @Operation(description = "Lista todos os clientes do usuario logado buscando pelo Cpf/Cnpj")
    @GetMapping("/cpfCnpj/{cpfCnpj}")
    public ResponseEntity<Customer> findByCpfCnpj(@PathVariable(name = "cpfCnpj")String cpfCnpj){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findByCpfCnpj(cpfCnpj));
    }
    @Operation(description = "Deleta um cliente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(name = "id")UUID id){
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
