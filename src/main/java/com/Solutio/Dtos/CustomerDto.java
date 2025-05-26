package com.Solutio.Dtos;

import com.Solutio.Models.Enums.Role;

public record CustomerDto(
        String name,
        String email,
        String password,
        String cpfCnpj,
        String phone,
        String address,
        Role role) {
}
