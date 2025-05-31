package com.Solutio.Services;


import com.Solutio.Dtos.AuthDto;
import com.Solutio.Dtos.RegisterDto;
import com.Solutio.Infra.Security.TokenService;
import com.Solutio.Models.Enums.Role;
import com.Solutio.Models.User;
import com.Solutio.Repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationManager authenticationManager;


    public String login(AuthDto authDto){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());

    }

    public String register(RegisterDto registerDto) {

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String passwordRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+=<>?{}\\[\\]~;:.,-]).{8,}$";

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
