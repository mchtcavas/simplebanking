package com.mchtcavas.simplebank.pipe;

import com.mchtcavas.simplebank.domain.TransactionType;

public abstract class BaseTransactionPipe implements TransactionPipe {
    TransactionType type;
    double amount;
    double balance;
    String providerName;
    String billNumber;
    @Override
    public TransactionType getType() {
        return this.type;
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getProviderName() {
        return providerName;
    }

    @Override
    public String getBillNumber() {
        return billNumber;
    }
}
