package com.Solutio.Controllers;

import com.Solutio.Models.PaymentTransaction;
import com.Solutio.Services.PaymentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment-transaction")
public class PaymentTransactionController {

    @Autowired
    PaymentTransactionService paymentTransactionService;

    @GetMapping
    public ResponseEntity<List<PaymentTransaction>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(paymentTransactionService.findAll());
    }
}
