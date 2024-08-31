package com.mchtcavas.simplebank.service;

import com.mchtcavas.simplebank.domain.TransactionType;

public class DepositTransaction implements TransactionPipe {
    private final TransactionType type = TransactionType.DEPOSIT_TRANSACTION;
    private final double amount;

    public DepositTransaction(double amount) {
        this.amount = amount;
    }

    @Override
    public double apply(double balance) {
        return balance + this.amount;
    }

    @Override
    public TransactionType getType() {
        return type;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "DepositTransaction{" +
                "type=" + type +
                ", amount=" + amount +
                '}';
    }
}
