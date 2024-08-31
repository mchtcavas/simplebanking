package com.mchtcavas.simplebank.service;

import com.mchtcavas.simplebank.domain.TransactionType;

public interface TransactionPipe {
    double apply(double balance);
    TransactionType getType();
    double getAmount();
}
