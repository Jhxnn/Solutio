package com.Solutio.Services;

import com.Solutio.Models.Boleto;
import com.Solutio.Models.Charge;
import com.Solutio.Models.Customer;
import com.Solutio.Models.User;
import com.Solutio.Repositories.BoletoRepository;
import com.Solutio.Repositories.ChargeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BoletoService {

    @Autowired
    BoletoRepository boletoRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    ChargeRepository chargeRepository;

    @Value("${asaas.api.token}")
    private String asaasApikey;

    public Boleto createBoletoCharge(Customer customer, Charge charge) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("access_token", asaasApikey);

        Map<String, Object> payload = new HashMap<>();
        payload.put("customer", customer.getExternalId());
        payload.put("billingType", "BOLETO");
        payload.put("value", charge.getAmount());
        payload.put("dueDate", charge.getDueDate().toString());
        payload.put("description", charge.getDescription());
        payload.put("externalReference", "CHG_" + UUID.randomUUID());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                "https://sandbox.asaas.com/api/v3/payments",
                request,
                JsonNode.class
        );

        JsonNode body = response.getBody();

        Boleto boleto = new Boleto();
        boleto.setCharge(charge);
        boleto.setExternalId(body.get("id").asText());
        boleto.setInvoiceUrl(body.get("invoiceUrl").asText());
        boleto.setBarcode(body.get("bankSlipUrl").asText());

        return boletoRepository.save(boleto);
    }

    public Boleto findByCharge(Charge charge){
        return boletoRepository.findByCharge(charge);
    }
    public List<Boleto> findBoletoCharges(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Customer> customers = customerService.findByUser(user);

        List<Charge> charges = new ArrayList<>();
        for (Customer customer : customers) {
            charges.addAll(chargeRepository.findByCustomer(customer));
        }
        List<Boleto> boletos = new ArrayList<>();
        for(Charge charge : charges){
            Boleto boleto = findByCharge(charge);
            if(boleto != null){
                boletos.add(boleto);
            }
        }
        return boletos;

    }
    public Boleto findById(UUID id){
        return boletoRepository.findById(id).orElseThrow(()-> new RuntimeException("Cannot be found"));
    }

    public void deleteBoleto(UUID id){
        Boleto boleto = findById(id);
        boletoRepository.delete(boleto);
    }

    public List<Boleto> findAll(){
        return boletoRepository.findAll();
    }

}
