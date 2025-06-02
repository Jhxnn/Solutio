package com.Solutio.Services;


import com.Solutio.Dtos.AuthDto;
import com.Solutio.Dtos.RegisterDto;
import com.Solutio.Infra.Security.TokenService;
import com.Solutio.Models.*;
import com.Solutio.Models.Enums.ChargeStatus;
import com.Solutio.Models.Enums.Role;
import com.Solutio.Repositories.BoletoRepository;
import com.Solutio.Repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    PixService pixService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ChargeService chargeService;

    @Autowired
    BoletoService boletoService;

    public String login(AuthDto authDto){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());

    }

    public String register(RegisterDto registerDto) {

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String passwordRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+=<>?{}\\[\\]~;:.,-]).{8,}$";

        if(userRepository.existsByEmail(registerDto.email())){
            throw new RuntimeException("The email is already exist");

        }

        if (!registerDto.email().matches(emailRegex)) {
            throw new RuntimeException("Invalid email format");
        }

        if (!registerDto.password().matches(passwordRegex)) {
            throw new RuntimeException("Password must be at least 8 characters long, contain one uppercase letter and one special character");
        }

        if (userRepository.findByEmail(registerDto.email()) != null) {
            throw new RuntimeException("Email is already in use");
        }

        String encryptedPass = new BCryptPasswordEncoder().encode(registerDto.password());
        User user = new User();
        BeanUtils.copyProperties(registerDto, user);
        user.setRole(Role.COMMON);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(encryptedPass);
        emailService.sendTextEmail(
                user.getEmail(),
                "Account Created - SOLUTIO",
                "Your account has been successfully created.\nWelcome to SOLUTIO!"
        );
        userRepository.save(user);
        return "Account created";
    }












}
