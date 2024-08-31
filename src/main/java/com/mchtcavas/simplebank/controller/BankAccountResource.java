package com.mchtcavas.simplebank.controller;

import com.mchtcavas.simplebank.dto.BankAccountDto;
import com.mchtcavas.simplebank.dto.BankAccountRequest;
import com.mchtcavas.simplebank.service.BankAccountManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/bank-accounts")
public class BankAccountResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BankAccountManagement bankAccountManagement;

    public BankAccountResource(BankAccountManagement bankAccountManagement) {
        this.bankAccountManagement = bankAccountManagement;
    }

    @PostMapping("/v1/create")
    public ResponseEntity<BankAccountDto> createBankAccount(@RequestBody BankAccountRequest request) {
        log.debug("Received bank account request. Owner number: {}, Mail: {}", request.getOwner(), request.getMail());

        BankAccountDto bankAccountDto = this.bankAccountManagement.create(request.getOwner(), request.getMail());

        return ResponseEntity.ok(bankAccountDto);
    }
}
