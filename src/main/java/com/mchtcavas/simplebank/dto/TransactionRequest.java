package com.mchtcavas.simplebank.dto;

public class TransactionRequest {
    private double amount;
    private String accountNumber;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "amount=" + amount +
                ", accountNumber=" + accountNumber +
                '}';
    }
}
