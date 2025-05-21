package com.Solutio.Services;

import com.Solutio.Dtos.ChargeDto;
import com.Solutio.Models.Charge;
import com.Solutio.Models.Customer;
import com.Solutio.Repositories.ChargeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChargeService {

    @Autowired
    CustomerService customerService;

    @Autowired
    ChargeRepository chargeRepository;


    public Charge findById(UUID id){
        return chargeRepository.findById(id).orElseThrow(()-> new RuntimeException("Cannot be found"));
    }
    public List<Charge> findAll(){
        return chargeRepository.findAll();
    }

    public Charge createCharge(ChargeDto chargeDto){
        Customer customer = customerService.findById(chargeDto.customer());
        Charge charge = new Charge();
        BeanUtils.copyProperties(chargeDto, charge);
        charge.setCustomer(customer);
        charge.setCreatedAt(LocalDateTime.now());
        return chargeRepository.save(charge);
    }
}
