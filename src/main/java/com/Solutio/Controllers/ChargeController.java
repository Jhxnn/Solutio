package com.Solutio.Controllers;


import com.Solutio.Dtos.ChargeDto;
import com.Solutio.Models.Charge;
import com.Solutio.Models.Customer;
import com.Solutio.Models.Enums.ChargeStatus;
import com.Solutio.Models.User;
import com.Solutio.Services.ChargeService;
import com.Solutio.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(description = "Cria uma cobrança")
    @PostMapping
    public ResponseEntity<Charge> createCharge(@RequestBody ChargeDto chargeDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(chargeService.createCharge(chargeDto));
    }

    @Operation(description = "Lista todas as cobranças do usuario logado")
    @GetMapping("/user")
    public ResponseEntity<List<Charge>> findUserCharges(){
        return ResponseEntity.status(HttpStatus.OK).body(chargeService.findUserCharge());
    }

    @Operation(description = "Lista todas as cobranças do usuario logado pelo status")
    @GetMapping("/user/status/{status}")
    public ResponseEntity<List<Charge>> findByStatus(@PathVariable(name = "status")ChargeStatus status){
        return ResponseEntity.status(HttpStatus.OK).body(chargeService.findUserChargeByStatus(status));
    }

    @Operation(description = "Deleta uma cobrança pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Charge> deleteCharge(@PathVariable(name = "id")UUID id){
        chargeService.deleteCharge(id);
        return ResponseEntity.noContent().build();
    }




}


