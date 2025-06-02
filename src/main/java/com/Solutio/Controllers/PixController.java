package com.Solutio.Controllers;


import com.Solutio.Dtos.PixTransaction;
import com.Solutio.Models.Pix;
import com.Solutio.Services.PixService;
import com.Solutio.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pix")
public class    PixController {

    @Autowired
    PixService pixService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<Pix>> findALl(){
        return ResponseEntity.status(HttpStatus.OK).body(pixService.findAll());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Pix>> findUserPix(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findPixCharges());
    }


//
//    @GetMapping("/qrCode/{pixId}")
//    public ResponseEntity<PixTransaction> pixTransaction(@PathVariable(name = "pixId")UUID pixId){
//        return ResponseEntity.status(HttpStatus.OK).body(pixService.getPixTransaction(pixId));
//    }

}
