package com.mchtcavas.simplebank.service;

import com.mchtcavas.simplebank.dto.TransactionDto;

public interface TransactionManagement {
    TransactionDto debit(double amount, String accountNumber);
    TransactionDto credit(double amount, String accountNumber);
}
