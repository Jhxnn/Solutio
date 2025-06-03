package com.Solutio.Controllers;

import com.Solutio.Models.Boleto;
import com.Solutio.Services.BoletoService;
import com.Solutio.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/boleto")
public class BoletoController {

    @Autowired
    BoletoService boletoService;


    @Operation(description = "Lista todos os boletos")
    @GetMapping("/all")
    public ResponseEntity<List<Boleto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(boletoService.findAll());
    }
    @Operation(description = "Lista todos os boletos do usuario")
    @GetMapping("/user")
    public ResponseEntity<List<Boleto>> findUserBoleto(){
        return ResponseEntity.status(HttpStatus.OK).body(boletoService.findBoletoCharges());
    }

    @Operation(description = "Deleta boleto pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boleto> deleteBoleto(@PathVariable(name = "id") UUID id){
        boletoService.deleteBoleto(id);
        return ResponseEntity.noContent().build();
    }
}
