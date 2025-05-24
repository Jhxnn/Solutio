package com.Solutio.Controllers;


import com.Solutio.Models.Pix;
import com.Solutio.Services.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pix")
public class PixController {


    @Autowired
    PixService pixService;

    public ResponseEntity<List<Pix>> findALl(){
        return ResponseEntity.status(HttpStatus.OK).body(pixService.findAll());
    }
}
