package com.Solutio.Controllers;

import com.Solutio.Dtos.AuthDto;
import com.Solutio.Dtos.RegisterDto;
import com.Solutio.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Operation(description = "Realiza login")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDto authDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(authDto));
    }

    @Operation(description = "Realiza cadastro")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.register(registerDto));
    }


}
