package com.mchtcavas.simplebank.dto;

public class BillPaymentTransactionRequest {
    private double amount;
    private String providerName;
    private String billNumber;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    @Override
    public String toString() {
        return "BillPaymentTransactionRequest{" +
                "amount=" + amount +
                ", providerName='" + providerName + '\'' +
                ", billNumber='" + billNumber + '\'' +
                '}';
    }
}
