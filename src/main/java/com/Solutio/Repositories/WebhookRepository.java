package com.Solutio.Repositories;

import com.Solutio.Models.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WebhookRepository extends JpaRepository<Webhook, UUID> {
}
