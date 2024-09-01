package com.mchtcavas.simplebank.pipe;

import com.mchtcavas.simplebank.domain.TransactionType;

public class BillPaymentTransactionPipeFactory implements PipeFactory{
    private final TransactionType type;
    private final double amount;
    private final double balance;
    private final String providerName;
    private final String billNumber;
    public BillPaymentTransactionPipeFactory(TransactionType type, double amount, double balance,
                                             String providerName, String billNumber) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.providerName = providerName;
        this.billNumber = billNumber;
    }
    @Override
    public TransactionPipe createTransactionPipe() {
        if (this.type == null) {
            return null;
        }

        if (type == TransactionType.PHONE_BILL_PAYMENT_TRANSACTION) {
            return new PhoneBillPaymentTransaction(providerName, billNumber, amount, balance);
        }

        return null;
    }
}
