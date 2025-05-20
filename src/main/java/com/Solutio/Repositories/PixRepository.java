package com.Solutio.Repositories;

import com.Solutio.Models.Pix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PixRepository extends JpaRepository<Pix, UUID> {
}
