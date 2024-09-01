package com.mchtcavas.simplebank.service;

import com.mchtcavas.simplebank.config.ApplicationProperties;
import com.mchtcavas.simplebank.domain.TransactionType;
import com.mchtcavas.simplebank.dto.BankAccountDto;
import com.mchtcavas.simplebank.dto.TransactionDto;
import com.mchtcavas.simplebank.pipe.BillPaymentTransactionPipeFactory;
import com.mchtcavas.simplebank.pipe.PipeFactory;
import com.mchtcavas.simplebank.pipe.TransactionPipe;
import com.mchtcavas.simplebank.pipe.TransactionPipeFactory;
import com.mchtcavas.simplebank.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionManagementImpl implements TransactionManagement {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BankAccountManagement bankAccountManagement;
    private final ValidationManager validationManager;
    private final ApplicationProperties applicationProperties;

    public TransactionManagementImpl(BankAccountManagement bankAccountManagement, ValidationManager validationManager, ApplicationProperties applicationProperties) {
        this.bankAccountManagement = bankAccountManagement;
        this.validationManager = validationManager;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public TransactionDto debit(double amount, String accountNumber) {
        log.debug("Starting debit operation with amount={} for account number={}", amount, accountNumber);

        BankAccountDto bankAccountDto = this.bankAccountManagement.check(accountNumber);
        log.debug("Checked bank account: {}", bankAccountDto.getAccountNumber());

        TransactionPipe transactionPipe = this.createTransaction(TransactionType.WITHDRAWAL_TRANSACTION, amount, bankAccountDto.getBalance());

        this.prepareDebitValidationChain(transactionPipe);
        log.debug("Executing validateOperation with transactionPipe: {}", transactionPipe);
        this.validationManager.validateOperation(transactionPipe);

        double newBalance = this.applyTransaction(transactionPipe, bankAccountDto);
        log.debug("Applied transaction. New balance: {}", newBalance);

        bankAccountDto.setBalance(newBalance);
        log.debug("Updated bank account balance to: {}", newBalance);

        TransactionDto transactionDto = this.prepareTransactionDto(transactionPipe);
        log.debug("Prepared TransactionDto: {}", transactionDto);

        bankAccountDto.getTransactionDtoList().add(transactionDto);
        log.debug("Added transaction to bank account's transaction list");

        this.bankAccountManagement.update(bankAccountDto);
        log.debug("Completed debit operation. Returning transaction details: {}", transactionDto);

        return transactionDto;
    }

    @Override
    public TransactionDto credit(double amount, String accountNumber) {
        log.debug("Starting credit operation with amount={} for account number={}", amount, accountNumber);

        BankAccountDto bankAccountDto = this.bankAccountManagement.check(accountNumber);
        log.debug("Checked bank account: {}", bankAccountDto.getAccountNumber());

        TransactionPipe transactionPipe = this.createTransaction(TransactionType.DEPOSIT_TRANSACTION, amount, bankAccountDto.getBalance());

        this.prepareCreditValidationChain(transactionPipe);
        log.debug("Executing validateOperation with transactionPipe: {}", transactionPipe);
        this.validationManager.validateOperation(transactionPipe);

        double newBalance = this.applyTransaction(transactionPipe, bankAccountDto);
        log.debug("Applied transaction. New balance: {}", newBalance);

        bankAccountDto.setBalance(newBalance);
        log.debug("Updated bank account balance to: {}", newBalance);

        TransactionDto transactionDto = this.prepareTransactionDto(transactionPipe);
        log.debug("Prepared TransactionDto: {}", transactionDto);

        bankAccountDto.getTransactionDtoList().add(transactionDto);
        log.debug("Added transaction to bank account's transaction list");

        this.bankAccountManagement.update(bankAccountDto);
        log.debug("Completed credit operation. Returning transaction details: {}", transactionDto);

        return transactionDto;
    }

    @Override
    public TransactionDto phoneBillPayment(String providerName, String billNumber, double amount, String accountNumber) {
        log.debug("Starting phone bill payment operation with amount={} for account number={}", amount, accountNumber);

        BankAccountDto bankAccountDto = this.bankAccountManagement.check(accountNumber);
        log.debug("Checked bank account: {}", bankAccountDto.getAccountNumber());

        TransactionPipe transactionPipe = this.createBillPaymentTransaction(TransactionType.PHONE_BILL_PAYMENT_TRANSACTION,
                amount, bankAccountDto.getBalance(), providerName, billNumber);

        this.preparePhoneBillPaymentValidationChain(transactionPipe);
        log.debug("Executing validateOperation with transactionPipe: {}", transactionPipe);
        this.validationManager.validateOperation(transactionPipe);

        double newBalance = this.applyTransaction(transactionPipe, bankAccountDto);
        log.debug("Applied transaction. New balance: {}", newBalance);

        bankAccountDto.setBalance(newBalance);
        log.debug("Updated bank account balance to: {}", newBalance);

        TransactionDto transactionDto = this.prepareTransactionDto(transactionPipe);
        log.debug("Prepared TransactionDto: {}", transactionDto);

        bankAccountDto.getTransactionDtoList().add(transactionDto);
        log.debug("Added transaction to bank account's transaction list");

        this.bankAccountManagement.update(bankAccountDto);
        log.debug("Completed phone bill payment operation. Returning transaction details: {}", transactionDto);

        return transactionDto;
    }

    private TransactionPipe createTransaction(TransactionType type, double amount, double balance) {
        log.debug("Entering createTransaction method with type={} and amount={}", type, amount);

        PipeFactory factory = new TransactionPipeFactory(type, amount, balance);
        TransactionPipe transaction = factory.createTransactionPipe();

        log.debug("Created transaction: {}", transaction);

        return transaction;
    }

    private TransactionPipe createBillPaymentTransaction(TransactionType type, double amount, double balance,
                                                         String providerName, String billNumber) {
        log.debug("Entering createBillPaymentTransaction method with type={}, amount={}, providerName: {}, billNumber: {}",
                type, amount, providerName, billNumber);

        PipeFactory factory = new BillPaymentTransactionPipeFactory(type, amount, balance, providerName, billNumber);
        TransactionPipe transaction = factory.createTransactionPipe();

        log.debug("Created bill payment transaction: {}", transaction);

        return transaction;
    }

    private double applyTransaction(TransactionPipe transactionPipe, BankAccountDto bankAccountDto) {
        log.debug("Entering applyTransaction method with transaction={} and initial balance={}", transactionPipe, bankAccountDto.getBalance());

        transactionPipe.apply();

        return transactionPipe.getBalance();
    }

    private TransactionDto prepareTransactionDto(TransactionPipe transaction) {
        log.debug("Entering prepareTransactionDto method with transaction={}", transaction);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setDate(LocalDateTime.now());
        transactionDto.setApprovalCode(UUID.randomUUID().toString());
        transactionDto.setType(transaction.getType());

        return transactionDto;
    }

    private void prepareDebitValidationChain(TransactionPipe transactionPipe) {
        ValidationChain chain = new ValidationChain();
        chain.addValidation(new InvalidTransactionAmount());
        chain.addValidation(new InsufficientFunds());

        this.validationManager.setValidationChain(transactionPipe, chain);
    }

    private void prepareCreditValidationChain(TransactionPipe transactionPipe) {
        ValidationChain chain = new ValidationChain();
        chain.addValidation(new InvalidTransactionAmount());

        this.validationManager.setValidationChain(transactionPipe, chain);
    }

    private void preparePhoneBillPaymentValidationChain(TransactionPipe transactionPipe) {
        ValidationChain chain = new ValidationChain();
        chain.addValidation(new InvalidTransactionAmount());
        chain.addValidation(new InsufficientFunds());
        chain.addValidation(new InvalidOperatorName(this.applicationProperties));
        chain.addValidation(new InvalidPhoneNumber());

        this.validationManager.setValidationChain(transactionPipe, chain);
    }
}
