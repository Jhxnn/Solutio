package com.Solutio.Controllers;


import com.Solutio.Dtos.ChargeDto;
import com.Solutio.Models.Charge;
import com.Solutio.Services.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charge")
public class ChargeController {

    @Autowired
    ChargeService chargeService;

    public ResponseEntity<Charge> createCharge(@RequestBody ChargeDto chargeDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(chargeService.createCharge(chargeDto));
    }



}


