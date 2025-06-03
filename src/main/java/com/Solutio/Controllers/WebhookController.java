package com.Solutio.Controllers;

import com.Solutio.Dtos.WebhookDto;
import com.Solutio.Models.Webhook;
import com.Solutio.Services.WebhookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    WebhookService webhookService;

    @Operation(description = "Webhook para atualizar os status das cobranças")
    @PostMapping
    public ResponseEntity<Void> receiveWebhook(@RequestBody WebhookDto webhookDto){
        webhookService.processWebhook(webhookDto);
        return ResponseEntity.noContent().build();
    }
    @Operation(description = "Lista todos os webhooks criados")
    @GetMapping
    public ResponseEntity<List<Webhook>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(webhookService.findAll());
    }
}
