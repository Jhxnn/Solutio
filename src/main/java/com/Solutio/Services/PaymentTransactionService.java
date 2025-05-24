package com.Solutio.Services;

import com.Solutio.Models.Charge;
import com.Solutio.Models.Enums.ChargeStatus;
import com.Solutio.Models.Enums.TransactionStatus;
import com.Solutio.Models.PaymentTransaction;
import com.Solutio.Repositories.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentTransactionService {

    @Autowired
    PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    EmailService emailService;

    public List<PaymentTransaction> findAll(){
        return paymentTransactionRepository.findAll();
    }
    public void createPaymentTransaction(Charge charge){
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCreatedAt(LocalDateTime.now());
        paymentTransaction.setCharge(charge);
        paymentTransaction.setMethod(charge.getType());
        paymentTransaction.setExternalId(charge.getExternalId());
        if(charge.getStatus() == ChargeStatus.PAID){
            emailService.sendTextEmail(charge.getCustomer().getEmail(), "PAYMENT MADE", "The payment has been made");
            paymentTransaction.setPaymentDate(LocalDateTime.now());
            paymentTransaction.setStatus(TransactionStatus.PAID);
        }
        if(charge.getStatus() == ChargeStatus.OVERDUE){
            emailService.sendTextEmail(charge.getCustomer().getEmail(), "PAYMENT FAILED", "The payment failed");
            paymentTransaction.setStatus(TransactionStatus.FAILED);
        }
        if(charge.getStatus() == ChargeStatus.PENDING){
            emailService.sendTextEmail(charge.getCustomer().getEmail(), "PAYMENT PENDING", "The payment is pending");
            paymentTransaction.setStatus(TransactionStatus.PENDING);
        }
        if(charge.getStatus() == ChargeStatus.CANCELED){
            emailService.sendTextEmail(charge.getCustomer().getEmail(), "PAYMENT CANCELED", "The pay has been canceled");
            paymentTransaction.setStatus(TransactionStatus.CANCELED);
        }
        paymentTransactionRepository.save(paymentTransaction);
    }
}
