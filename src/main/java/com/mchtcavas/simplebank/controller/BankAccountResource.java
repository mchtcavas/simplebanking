package com.mchtcavas.simplebank.controller;

import com.mchtcavas.simplebank.dto.BankAccountDto;
import com.mchtcavas.simplebank.dto.BankAccountRequest;
import com.mchtcavas.simplebank.service.BankAccountManagement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/bank-accounts")
public class BankAccountResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BankAccountManagement bankAccountManagement;

    public BankAccountResource(BankAccountManagement bankAccountManagement) {
        this.bankAccountManagement = bankAccountManagement;
    }

    @Operation(
            summary = "Create a new bank account",
            description = "Creates a new bank account for the given owner and mail. Returns the details of the created bank account."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the bank account",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccountDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/v1/create")
    public ResponseEntity<BankAccountDto> createBankAccount(@RequestBody BankAccountRequest request) {
        log.debug("Received create bank account request. Owner number: {}, Mail: {}", request.getOwner(), request.getMail());

        BankAccountDto bankAccountDto = this.bankAccountManagement.create(request.getOwner(), request.getMail());

        return ResponseEntity.ok(bankAccountDto);
    }

    @Operation(
            summary = "Retrieve a bank account",
            description = "Fetches the details of a bank account using the provided bank account number."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the bank account details",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccountDto.class))),
            @ApiResponse(responseCode = "404", description = "Bank account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/v1/{bankAccountNumber}")
    public ResponseEntity<BankAccountDto> getBankAccount(@PathVariable String bankAccountNumber) {
        log.debug("Received get bank account request. Owner bank account number: {}", bankAccountNumber);

        BankAccountDto bankAccountDto = this.bankAccountManagement.check(bankAccountNumber);

        return ResponseEntity.ok(bankAccountDto);
    }
}
