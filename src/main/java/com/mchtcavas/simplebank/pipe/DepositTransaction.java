package com.mchtcavas.simplebank.pipe;

import com.mchtcavas.simplebank.domain.TransactionType;

public class DepositTransaction extends BaseTransactionPipe {
    public DepositTransaction(double amount, double balance) {
        super.amount = amount;
        super.balance = balance;
        super.type = TransactionType.DEPOSIT_TRANSACTION;
    }

    @Override
    public void apply() {
        super.balance = super.balance + super.amount;
    }
}
