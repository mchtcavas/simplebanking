package com.mchtcavas.simplebank.controller;

import com.mchtcavas.simplebank.dto.TransactionDto;
import com.mchtcavas.simplebank.dto.TransactionRequest;
import com.mchtcavas.simplebank.service.TransactionManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/v1/debit")
    public ResponseEntity<TransactionDto> debitTransaction(@RequestBody TransactionRequest request) {
        log.debug("Received debit request. Account number: {}, Amount: {}", request.getAccountNumber(), request.getAmount());

        TransactionDto transactionDto = this.transactionManagement.debit(request.getAmount(), request.getAccountNumber());

        return ResponseEntity.ok(transactionDto);
    }

    @PostMapping("/v1/credit")
    public ResponseEntity<TransactionDto> creditTransaction(@RequestBody TransactionRequest request) {
        log.debug("Received credit request. Account number: {}, Amount: {}", request.getAccountNumber(), request.getAmount());

        TransactionDto transactionDto = this.transactionManagement.credit(request.getAmount(), request.getAccountNumber());

        return ResponseEntity.ok(transactionDto);
    }
}
