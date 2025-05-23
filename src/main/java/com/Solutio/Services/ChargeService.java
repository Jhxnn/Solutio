package com.Solutio.Services;

import com.Solutio.Dtos.ChargeDto;
import com.Solutio.Models.Charge;
import com.Solutio.Models.Customer;
import com.Solutio.Models.Enums.ChargeStatus;
import com.Solutio.Models.Enums.ChargeType;
import com.Solutio.Repositories.ChargeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    PixService pixService;

    @Autowired
    BoletoService boletoService;


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
        if(charge.getType() == ChargeType.PIX){
            pixService.createPixCharge(customer,charge);
        }
        if(charge.getType() == ChargeType.BOLETO){
            boletoService.createBoletoCharge(customer, charge);
        }
        charge.setCustomer(customer);
        charge.setCreatedAt(LocalDateTime.now());
        charge.setStatus(ChargeStatus.PENDING);
        return chargeRepository.save(charge);
    }

    public Charge updateCharge(ChargeDto chargeDto, UUID id){
        Charge charge = findById(id);
        if(chargeDto.customer() != null){
            Customer customer = customerService.findById(chargeDto.customer());
            charge.setCustomer(customer);
        }
        if(chargeDto.amount() != null){
            charge.setAmount(chargeDto.amount());
        }
        if(chargeDto.chargeType() != null){
            charge.setType(chargeDto.chargeType());
        }
        if(chargeDto.chargeType() != null){
            charge.setDueDate(chargeDto.dueDate());
        }
        if(chargeDto.description() != null){
            charge.setDescription(chargeDto.description());
        }
        charge.setUpdateAt(LocalDateTime.now());

        return chargeRepository.save(charge);
    }

    public Charge updateChargeStatus(ChargeStatus status, Charge charge){
        if(status == ChargeStatus.PAID){
            if(charge.getDueDate().isBefore(LocalDate.now())){
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "This charge is unavailable");
            }
            charge.setPaidAt(LocalDateTime.now());

        }
        charge.setStatus(status);
        charge.setUpdateAt(LocalDateTime.now());
        return chargeRepository.save(charge);
    }


    public List<Charge> findByCustomer(UUID id){
       Customer customer =  customerService.findById(id);
       return chargeRepository.findByCustomer(customer);
    }

    public List<Charge> findByStatus(ChargeStatus chargeStatus){
        return chargeRepository.findByStatus(chargeStatus);
    }

    public void deleteCharge(UUID id){
        Charge charge = findById(id);
        chargeRepository.delete(charge);
    }
}
