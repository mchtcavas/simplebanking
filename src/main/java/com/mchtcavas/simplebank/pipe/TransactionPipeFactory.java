package com.mchtcavas.simplebank.pipe;

import com.mchtcavas.simplebank.domain.TransactionType;

public class TransactionPipeFactory implements PipeFactory {
    private final TransactionType type;
    private final double amount;
    private final double balance;
    public TransactionPipeFactory(TransactionType type, double amount, double balance) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    @Override
    public TransactionPipe createTransactionPipe() {
        if (this.type == null) {
            return null;
        }

        if (this.type == TransactionType.DEPOSIT_TRANSACTION) {
            return new DepositTransaction(this.amount, this.balance);
        } else if (this.type == TransactionType.WITHDRAWAL_TRANSACTION) {
            return new WithdrawalTransaction(this.amount, this.balance);
        }

        return null;
    }
}
