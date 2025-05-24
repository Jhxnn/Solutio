package com.Solutio.Services;

import com.Solutio.Models.Boleto;
import com.Solutio.Models.Charge;
import com.Solutio.Models.Customer;
import com.Solutio.Repositories.BoletoRepository;
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
public class BoletoService {

    @Autowired
    BoletoRepository boletoRepository;

    @Value("${asaas.api.token}")
    private String asaasApikey;

    public void createBoletoCharge(Customer customer, Charge charge) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("access_token", asaasApikey);

        Map<String, Object> payload = new HashMap<>();
        payload.put("customer", customer.getExternalId());
        payload.put("billingType", "BOLETO");
        payload.put("value", charge.getAmount());
        payload.put("dueDate", charge.getDueDate().toString());
        payload.put("description", "Pagamento de serviço via boleto");
        payload.put("externalReference", "CHG_" + UUID.randomUUID());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                "https://www.asaas.com/api/v3/payments",
                request,
                JsonNode.class
        );

        JsonNode body = response.getBody();

        Boleto boleto = new Boleto();
        boleto.setCharge(charge);
        boleto.setExternalId(body.get("id").asText());
        boleto.setInvoiceUrl(body.get("invoiceUrl").asText());
        boleto.setBarcode(body.get("bankSlipUrl").asText());

        boletoRepository.save(boleto);
    }

    public List<Boleto> findAll(){
        return boletoRepository.findAll();
    }

}
