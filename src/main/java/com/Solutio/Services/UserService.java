package com.Solutio.Services;


import com.Solutio.Dtos.AuthDto;
import com.Solutio.Dtos.RegisterDto;
import com.Solutio.Infra.Security.TokenService;
import com.Solutio.Models.Enums.Role;
import com.Solutio.Models.User;
import com.Solutio.Repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
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
    private AuthenticationManager authenticationManager;


    public String login(AuthDto authDto){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());

    }

    public String register(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.email()) != null) {
            throw new RuntimeException("Email is already use");
        }

        String encryptedPass = new BCryptPasswordEncoder().encode(registerDto.password());
        User user = new User();
        BeanUtils.copyProperties(registerDto, user);
        user.setRole(Role.COMMON);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(encryptedPass);
        emailService.sendTextEmail(user.getEmail(), "Account created - SOLUTIO", "Your account has been created. \nWelcome to Solutio");
        userRepository.save(user);
        return "Account created";
    }

}
