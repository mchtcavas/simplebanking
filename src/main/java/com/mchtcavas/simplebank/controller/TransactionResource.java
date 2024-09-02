package com.mchtcavas.simplebank.controller;

import com.mchtcavas.simplebank.dto.BillPaymentTransactionRequest;
import com.mchtcavas.simplebank.dto.TransactionDto;
import com.mchtcavas.simplebank.dto.TransactionRequest;
import com.mchtcavas.simplebank.service.TransactionManagement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Perform a debit transaction",
            description = "Debits a specified amount from the given account number."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully debited the account",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data or insufficient funds"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/v1/debit/{accountNumber}")
    public ResponseEntity<TransactionDto> debitTransaction(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
        log.debug("Received debit request. Account number: {}, Amount: {}", accountNumber, request.getAmount());

        TransactionDto transactionDto = this.transactionManagement.debit(request.getAmount(), accountNumber);

        return ResponseEntity.ok(transactionDto);
    }

    @Operation(
            summary = "Perform a credit transaction",
            description = "Credits a specified amount to the given account number."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully credited the account",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/v1/credit/{accountNumber}")
    public ResponseEntity<TransactionDto> creditTransaction(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
        log.debug("Received credit request. Account number: {}, Amount: {}", accountNumber, request.getAmount());

        TransactionDto transactionDto = this.transactionManagement.credit(request.getAmount(), accountNumber);

        return ResponseEntity.ok(transactionDto);
    }

    @Operation(
            summary = "Perform a phone bill payment transaction",
            description = "Pays a phone bill using the provided account number, provider name, and bill number."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully processed phone bill payment",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/v1/phone-bill-payment/{accountNumber}")
    public ResponseEntity<TransactionDto> phoneBillPaymentTransaction(@PathVariable String accountNumber, @RequestBody BillPaymentTransactionRequest request) {
        log.debug("Received phone bill payment request. Account number: {}, Provider Name: {}, Bill Number: {}, Amount: {}", accountNumber, request.getProviderName(), request.getBillNumber(), request.getAmount());

        TransactionDto transactionDto = this.transactionManagement.phoneBillPayment(request.getProviderName(), request.getBillNumber(), request.getAmount(), accountNumber);

        return ResponseEntity.ok(transactionDto);
    }
}
