package com.mchtcavas.simplebank.domain;

public enum TransactionType {
    DEPOSIT_TRANSACTION("DepositTransaction"),
    WITHDRAWAL_TRANSACTION("WithdrawalTransaction"),
    PHONE_BILL_PAYMENT_TRANSACTION("PhoneBillPaymentTransaction");
    private final String name;

    TransactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
