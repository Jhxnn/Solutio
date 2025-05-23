package com.Solutio.Services;

import com.Solutio.Dtos.WebhookDto;
import com.Solutio.Models.Charge;
import com.Solutio.Models.Enums.ChargeStatus;
import com.Solutio.Repositories.ChargeRepository;
import com.Solutio.Repositories.WebhookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {

    @Autowired
    ChargeRepository chargeRepository;

    @Autowired
    WebhookRepository webhookRepository;

    @Autowired
    ChargeService chargeService;

    public void processWebhook(WebhookDto payload) {
        Charge charge = chargeRepository.findByExternalId(payload.paymentData().id());
        switch (payload.paymentData().status().toUpperCase()) {
            case "PAID" -> chargeService.updateChargeStatus(ChargeStatus.PAID, charge);
            case "CANCELLED" -> chargeService.updateChargeStatus(ChargeStatus.CANCELED, charge);
            case "EXPIRED" -> chargeService.updateChargeStatus(ChargeStatus.OVERDUE, charge);
            default -> System.out.println("Evento não tratado.");
        }
    }

}
