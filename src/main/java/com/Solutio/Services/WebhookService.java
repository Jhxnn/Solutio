package com.Solutio.Services;

import com.Solutio.Dtos.WebhookDto;
import com.Solutio.Models.Charge;
import com.Solutio.Models.Enums.ChargeStatus;
import com.Solutio.Models.Webhook;
import com.Solutio.Repositories.ChargeRepository;
import com.Solutio.Repositories.WebhookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WebhookService {

    @Autowired
    ChargeRepository chargeRepository;

    @Autowired
    WebhookRepository webhookRepository;

    @Autowired
    ChargeService chargeService;

    public void processWebhook(WebhookDto payload) {
        Charge charge = chargeRepository.findByExternalId(payload.payment().id());
        Webhook webhook = new Webhook();
        webhook.setEvent(payload.event());
        webhook.setPayload(payload);
        webhook.setReceivedAt(LocalDateTime.now());

        webhookRepository.save(webhook);
        switch (payload.payment().status().toUpperCase()) {
            case "RECEIVED" -> chargeService.updateChargeStatus(ChargeStatus.PAID, charge);
            case "REFUNDED" -> chargeService.updateChargeStatus(ChargeStatus.CANCELED, charge);
            case "OVERDUE" -> chargeService.updateChargeStatus(ChargeStatus.OVERDUE, charge);
            default -> webhook.setProcessed(false);
        }
        webhook.setProcessed(true);
        webhookRepository.save(webhook);
    }

}
