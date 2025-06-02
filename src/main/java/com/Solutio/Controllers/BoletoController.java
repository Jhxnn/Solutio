package com.Solutio.Controllers;

import com.Solutio.Models.Boleto;
import com.Solutio.Services.BoletoService;
import com.Solutio.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/boleto")
public class BoletoController {

    @Autowired
    BoletoService boletoService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<Boleto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(boletoService.findAll());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Boleto>> findUserBoleto(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findBoletoCharges());
    }
}
