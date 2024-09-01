package com.mchtcavas.simplebank.pipe;

import com.mchtcavas.simplebank.domain.TransactionType;

public class PhoneBillPaymentTransaction extends BaseTransactionPipe {
    public PhoneBillPaymentTransaction(String providerName, String billNumber, double amount, double balance) {
        super.amount = amount;
        super.balance = balance;
        super.type = TransactionType.PHONE_BILL_PAYMENT_TRANSACTION;
        super.billNumber = billNumber;
        super.providerName = providerName;
    }
    @Override
    public void apply() {
        //third party service call
        super.balance = super.balance - super.amount;
    }
}
