package com.Solutio.Controllers;


import com.Solutio.Dtos.ChargeDto;
import com.Solutio.Models.Charge;
import com.Solutio.Models.Enums.ChargeStatus;
import com.Solutio.Services.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/charge")
public class ChargeController {

    @Autowired
    ChargeService chargeService;

    @PostMapping
    public ResponseEntity<Charge> createCharge(@RequestBody ChargeDto chargeDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(chargeService.createCharge(chargeDto));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Charge>> findByStatus(@PathVariable(name = "status") ChargeStatus status){
        return ResponseEntity.status(HttpStatus.OK).body(chargeService.findByStatus(status));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Charge>> findByCustomer(@PathVariable(name = "id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(chargeService.findByCustomer(id));
    }



}


