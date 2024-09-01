package com.mchtcavas.simplebank.validation;

import com.mchtcavas.simplebank.pipe.TransactionPipe;

public interface CheckTransaction {
    void validate(TransactionPipe pipe);
}
