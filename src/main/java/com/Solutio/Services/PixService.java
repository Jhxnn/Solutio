package com.Solutio.Services;


import com.Solutio.Models.Charge;
import com.Solutio.Models.Customer;
import com.Solutio.Models.Pix;
import com.Solutio.Repositories.PixRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PixService {


    @Autowired
    PixRepository pixRepository;

    @Value("${asaas.api.token}")
    private String asaasApikey;

    public Pix createPixCharge(Customer customer,Charge charge){

        Pix pix = new Pix();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("access_token", asaasApikey);

        Map<String, Object> payload = new HashMap<>();
        payload.put("customer", customer.getExternalId());
        payload.put("billingType", "PIX");
        payload.put("value", charge.getAmount());
        payload.put("dueDate", charge.getDueDate().toString());
        payload.put("description", "Pagamento serviço");
        payload.put("externalReference", "CHG_" + UUID.randomUUID());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                "https://sandbox.asaas.com/api/v3/payments",
                request,
                JsonNode.class
        );

        JsonNode body = response.getBody();

        pix.setCharge(charge);
        pix.setQrCode(body.has("pixQrCode") ? body.get("pixQrCode").asText() : null);
        pix.setQrCodeUrl(body.has("pixQrCodeImage") ? body.get("pixQrCodeImage").asText() : null);
        pix.setExternalId(body.has("id") ? body.get("id").asText() : null);
        pix.setInvoiceUrl(body.has("invoiceUrl") ? body.get("invoiceUrl").asText() : null);

        return pixRepository.save(pix);
    }

    public List<Pix> findAll(){
        return pixRepository.findAll();
    }
}
