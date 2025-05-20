package com.Solutio.Services;

import com.Solutio.Dtos.CustomerDto;
import com.Solutio.Models.Customer;
import com.Solutio.Repositories.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer findById(UUID id){
        return customerRepository.findById(id).orElseThrow(()-> new RuntimeException("Cannot be found"));
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer createCustomer(CustomerDto customerDto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        customer.setRegistrationDate(LocalDate.now());
        customerRepository.save(customer);
        return customer;
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
}
