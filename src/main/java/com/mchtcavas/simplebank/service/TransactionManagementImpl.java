package com.mchtcavas.simplebank.service;

import com.mchtcavas.simplebank.domain.TransactionType;
import com.mchtcavas.simplebank.dto.BankAccountDto;
import com.mchtcavas.simplebank.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionManagementImpl implements TransactionManagement {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BankAccountManagement bankAccountManagement;

    public TransactionManagementImpl(BankAccountManagement bankAccountManagement) {
        this.bankAccountManagement = bankAccountManagement;
    }

    @Override
    public TransactionDto debit(double amount, String accountNumber) {
        log.debug("Starting debit operation with amount={} for account number={}", amount, accountNumber);

        BankAccountDto bankAccountDto = this.bankAccountManagement.check(accountNumber);
        log.debug("Checked bank account: {}", bankAccountDto.getAccountNumber());

        TransactionPipe transactionPipe = this.createTransaction(TransactionType.WITHDRAWAL_TRANSACTION, amount);

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

        TransactionPipe transactionPipe = this.createTransaction(TransactionType.DEPOSIT_TRANSACTION, amount);

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

    private TransactionPipe createTransaction(TransactionType type, double amount) {
        log.debug("Entering createTransaction method with type={} and amount={}", type, amount);

        TransactionPipeFactory factory = new TransactionPipeFactory();
        TransactionPipe transaction = factory.getTransaction(type, amount);

        log.debug("Created transaction: {}", transaction);

        return transaction;
    }

    private double applyTransaction(TransactionPipe transactionPipe, BankAccountDto bankAccountDto) {
        log.debug("Entering applyTransaction method with transaction={} and initial balance={}", transactionPipe, bankAccountDto.getBalance());

        return transactionPipe.apply(bankAccountDto.getBalance());
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
}
