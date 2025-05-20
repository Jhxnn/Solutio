package com.Solutio.Dtos;

public record CustomerDto(
        String name,
        String email,
        String cpfCnpj,
        String phone,
        String address) {
}
