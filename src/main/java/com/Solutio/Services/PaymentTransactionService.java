package com.Solutio.Services;

import com.Solutio.Models.Charge;
import com.Solutio.Models.Enums.ChargeStatus;
import com.Solutio.Models.Enums.TransactionStatus;
import com.Solutio.Models.PaymentTransaction;
import com.Solutio.Repositories.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentTransactionService {

    @Autowired
    PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    EmailService emailService;

    public void createPaymentTransaction(Charge charge){
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCreatedAt(LocalDateTime.now());
        paymentTransaction.setCharge(charge);
        paymentTransaction.setMethod(charge.getType());
        paymentTransaction.setExternalId(charge.getExternalId());
        if(charge.getStatus() == ChargeStatus.PAID){
            paymentTransaction.setPaymentDate(LocalDateTime.now());
            paymentTransaction.setStatus(TransactionStatus.PAID);
        }
        if(charge.getStatus() == ChargeStatus.OVERDUE){
            paymentTransaction.setStatus(TransactionStatus.FAILED);
        }
        if(charge.getStatus() == ChargeStatus.PENDING){
            paymentTransaction.setStatus(TransactionStatus.PENDING);
        }
        if(charge.getStatus() == ChargeStatus.CANCELED){
            paymentTransaction.setStatus(TransactionStatus.CANCELED);
        }
        paymentTransactionRepository.save(paymentTransaction);
    }
}
