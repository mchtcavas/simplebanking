package com.mchtcavas.simplebank.validation;

import com.mchtcavas.simplebank.exception.BusinessException;
import com.mchtcavas.simplebank.exception.BusinessExceptionKey;
import com.mchtcavas.simplebank.pipe.TransactionPipe;

public class InvalidTransactionAmount implements CheckTransaction{
    @Override
    public void validate(TransactionPipe pipe) {
        if (pipe.getAmount() <= 0) {
            throw new BusinessException(BusinessExceptionKey.INVALID_TRANSACTION_AMOUNT);
        }
    }
}
