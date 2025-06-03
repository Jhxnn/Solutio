package com.Solutio.Controllers;


import com.Solutio.Dtos.PixTransaction;
import com.Solutio.Models.Pix;
import com.Solutio.Services.PixService;
import com.Solutio.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pix")
public class PixController {

    @Autowired
    PixService pixService;

    @GetMapping("/all")
    public ResponseEntity<List<Pix>> findALl(){
        return ResponseEntity.status(HttpStatus.OK).body(pixService.findAll());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Pix>> findUserPix(){
        return ResponseEntity.status(HttpStatus.OK).body(pixService.findPixCharges());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Pix> deletePix(@PathVariable(name = "id")UUID id){
        pixService.deletePix(id);
        return ResponseEntity.noContent().build();
    }


//
//    @GetMapping("/qrCode/{pixId}")
//    public ResponseEntity<PixTransaction> pixTransaction(@PathVariable(name = "pixId")UUID pixId){
//        return ResponseEntity.status(HttpStatus.OK).body(pixService.getPixTransaction(pixId));
//    }

}
