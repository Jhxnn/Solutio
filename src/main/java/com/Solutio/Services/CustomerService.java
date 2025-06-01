package com.Solutio.Services;

import com.Solutio.Dtos.CustomerDto;
import com.Solutio.Models.Customer;
import com.Solutio.Models.User;
import com.Solutio.Repositories.CustomerRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Value("${asaas.api.token}")
    private String asaasApiKey;


    public Customer findById(UUID id){
        return customerRepository.findById(id).orElseThrow(()-> new RuntimeException("Cannot be found"));
    }

    public Customer findByEmail(String email) {
        List<Customer> customers = findByUser();
        return customers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found or does not belong to this user"));
    }

    public List<Customer> findByUser(User user){
        return customerRepository.findByUser(user);
    }

    public List<Customer> findByUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByUser(user);
    }

    public Customer findByCpfCnpj(String cpfCnpj) {
        List<Customer> customers = findByUser();
        return customers.stream()
                .filter(customer -> customer.getCpfCnpj().equals(cpfCnpj))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found or does not belong to this user"));
    }


    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String cpfCnpjRegex = "^\\d{11}$|^\\d{14}$";

        if (!customerDto.email().matches(emailRegex)) {
            throw new RuntimeException("Invalid email format");
        }
        if (!customerDto.cpfCnpj().matches(cpfCnpjRegex)) {
            throw new RuntimeException("Invalid CPF or CNPJ format");
        }
        if(customerRepository.existsByEmail(customerDto.email())){
            throw new RuntimeException("The email already exists");
        }
        if(customerRepository.existsByCpfCnpj(customerDto.cpfCnpj())){
            throw new RuntimeException("Cpf or Cnpj already registered");
        }
        BeanUtils.copyProperties(customerDto,customer);
        customer.setRegistrationDate(LocalDate.now());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("access_token", asaasApiKey);

        Map<String, Object> payload = new HashMap<>();
        payload.put("name", customer.getName());
        payload.put("cpfCnpj", customer.getCpfCnpj());
        payload.put("email", customer.getEmail());
        payload.put("mobilePhone", customer.getPhone());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                "https://sandbox.asaas.com/api/v3/customers",
                request,
                JsonNode.class
        );

        String externalId = response.getBody().get("id").asText();
        customer.setExternalId(externalId);

        return customerRepository.save(customer);

    }


    public Customer updateCustomer(CustomerDto customerDto, UUID id){
        Customer customer = findById(id);
        if(customerDto.name() != null){
            customer.setName(customerDto.name());
        }
        if(customerDto.email() != null){
            customer.setEmail(customerDto.email());
        }
        if(customerDto.cpfCnpj() != null){
            customer.setCpfCnpj(customerDto.cpfCnpj());
        }
        if(customerDto.address() != null){
            customer.setAddress(customerDto.address());
        }
        if(customerDto.phone() != null){
            customer.setPhone(customerDto.phone());
        }
        customer.setUpdateDate(LocalDate.now());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(UUID id){
        Customer customer = findById(id);
        customerRepository.delete(customer);
    }
}
