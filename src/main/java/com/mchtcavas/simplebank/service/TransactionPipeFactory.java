package com.mchtcavas.simplebank.service;

import com.mchtcavas.simplebank.domain.TransactionType;

public class TransactionPipeFactory {
    public TransactionPipe getTransaction(TransactionType type, double amount) {
        if (type == null) {
            return null;
        }

        if (type == TransactionType.DEPOSIT_TRANSACTION) {
            return new DepositTransaction(amount);
        } else if (type == TransactionType.WITHDRAWAL_TRANSACTION) {
            return new WithdrawalTransaction(amount);
        }

        return null;
    }
}
