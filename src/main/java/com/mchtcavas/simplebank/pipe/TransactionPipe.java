package com.mchtcavas.simplebank.pipe;

import com.mchtcavas.simplebank.domain.TransactionType;

public interface TransactionPipe {
    void apply();
    TransactionType getType();
    double getAmount();
    double getBalance();
    String getProviderName();
    String getBillNumber();

}
