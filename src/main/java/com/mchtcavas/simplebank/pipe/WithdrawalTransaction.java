package com.mchtcavas.simplebank.pipe;

import com.mchtcavas.simplebank.domain.TransactionType;

public class WithdrawalTransaction extends BaseTransactionPipe {
    public WithdrawalTransaction(double amount, double balance) {
        super.amount = amount;
        super.balance = balance;
        super.type = TransactionType.WITHDRAWAL_TRANSACTION;
    }

    @Override
    public void apply() {
        super.balance = super.balance - super.amount;
    }
}
