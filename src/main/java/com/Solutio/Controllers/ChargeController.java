package com.Solutio.Controllers;


import com.Solutio.Dtos.ChargeDto;
import com.Solutio.Models.Charge;
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

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Charge> createCharge(@RequestBody ChargeDto chargeDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(chargeService.createCharge(chargeDto));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Charge>> findByStatus(@PathVariable(name = "status") ChargeStatus status){
        return ResponseEntity.status(HttpStatus.OK).body(chargeService.findByStatus(status));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Charge>> findUserCharges(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserCharge());
    }

    @GetMapping("/user/status/{status}")
    public ResponseEntity<List<Charge>> findByStatus(@PathVariable(name = "status")String status){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserChargeByStatus(status));
    }




}


