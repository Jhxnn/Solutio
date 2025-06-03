package com.Solutio.Controllers;


import com.Solutio.Dtos.ChargeDto;
import com.Solutio.Models.Charge;
import com.Solutio.Models.Customer;
import com.Solutio.Models.Enums.ChargeStatus;
import com.Solutio.Models.User;
import com.Solutio.Services.ChargeService;
import com.Solutio.Services.UserService;
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

    @GetMapping("/user")
    public ResponseEntity<List<Charge>> findUserCharges(){
        return ResponseEntity.status(HttpStatus.OK).body(chargeService.findUserCharge());
    }

    @GetMapping("/user/status/{status}")
    public ResponseEntity<List<Charge>> findByStatus(@PathVariable(name = "status")ChargeStatus status){
        return ResponseEntity.status(HttpStatus.OK).body(chargeService.findUserChargeByStatus(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Charge> deleteCharge(@PathVariable(name = "id")UUID id){
        chargeService.deleteCharge(id);
        return ResponseEntity.noContent().build();
    }




}


