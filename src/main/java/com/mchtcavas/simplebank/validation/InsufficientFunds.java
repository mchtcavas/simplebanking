package com.mchtcavas.simplebank.validation;

import com.mchtcavas.simplebank.exception.BusinessException;
import com.mchtcavas.simplebank.exception.BusinessExceptionKey;
import com.mchtcavas.simplebank.pipe.TransactionPipe;

public class InsufficientFunds implements CheckTransaction{
    @Override
    public void validate(TransactionPipe pipe) {
        if (pipe.getBalance() < pipe.getAmount()) {
            throw new BusinessException(BusinessExceptionKey.INSUFFICIENT_FUNDS);
        }
    }
}
