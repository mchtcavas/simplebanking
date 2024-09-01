package com.mchtcavas.simplebank.dto;

public class TransactionRequest {
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "amount=" + amount +
                '}';
    }
}
