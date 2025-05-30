package com.Solutio.Controllers;

import com.Solutio.Dtos.WebhookDto;
import com.Solutio.Services.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    WebhookService webhookService;

    @PostMapping
    public ResponseEntity<Void> receiveWebhook(@RequestBody WebhookDto webhookDto){
        webhookService.processWebhook(webhookDto);
        System.out.println(webhookDto.event());
        System.out.println(webhookDto.payment());
        System.out.println(webhookDto.payment().status());
        return ResponseEntity.noContent().build();
    }
}
