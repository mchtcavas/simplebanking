package com.mchtcavas.simplebank.util;

import com.mchtcavas.simplebank.domain.BankAccount;
import com.mchtcavas.simplebank.domain.Transaction;
import com.mchtcavas.simplebank.dto.TransactionDto;

public class TransactionMapper {
    public static Transaction toTransaction(TransactionDto transactionDto, BankAccount bankAccount) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate());
        transaction.setType(transactionDto.getType());
        transaction.setApprovalCode(transactionDto.getApprovalCode());
        transaction.setBankAccount(bankAccount);
        return transaction;
    }

    public static TransactionDto toTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setType(transaction.getType());
        transactionDto.setDate(transaction.getDate());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setApprovalCode(transaction.getApprovalCode());
        return transactionDto;
    }
}
