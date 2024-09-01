package com.mchtcavas.simplebank.controller;

import com.mchtcavas.simplebank.dto.BillPaymentTransactionRequest;
import com.mchtcavas.simplebank.dto.TransactionDto;
import com.mchtcavas.simplebank.dto.TransactionRequest;
import com.mchtcavas.simplebank.service.TransactionManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/transactions")
public class TransactionResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TransactionManagement transactionManagement;

    public TransactionResource(TransactionManagement transactionManagement) {
        this.transactionManagement = transactionManagement;
    }

    @PostMapping("/v1/debit/{accountNumber}")
    public ResponseEntity<TransactionDto> debitTransaction(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
        log.debug("Received debit request. Account number: {}, Amount: {}", accountNumber, request.getAmount());

        TransactionDto transactionDto = this.transactionManagement.debit(request.getAmount(), accountNumber);

        return ResponseEntity.ok(transactionDto);
    }

    @PostMapping("/v1/credit/{accountNumber}")
    public ResponseEntity<TransactionDto> creditTransaction(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
        log.debug("Received credit request. Account number: {}, Amount: {}", accountNumber, request.getAmount());

        TransactionDto transactionDto = this.transactionManagement.credit(request.getAmount(), accountNumber);

        return ResponseEntity.ok(transactionDto);
    }

    @PostMapping("/v1/phone-bill-payment/{accountNumber}")
    public ResponseEntity<TransactionDto> phoneBillPaymentTransaction(@PathVariable String accountNumber, @RequestBody BillPaymentTransactionRequest request) {
        log.debug("Received phone bill payment request. Account number: {}, Provider Name: {}, Bill Number: {}, Amount: {}", accountNumber, request.getProviderName(), request.getBillNumber(), request.getAmount());

        TransactionDto transactionDto = this.transactionManagement.phoneBillPayment(request.getProviderName(), request.getBillNumber(), request.getAmount(), accountNumber);

        return ResponseEntity.ok(transactionDto);
    }
}
