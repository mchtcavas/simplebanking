package com.mchtcavas.simplebank.validation;

import com.mchtcavas.simplebank.config.ApplicationProperties;
import com.mchtcavas.simplebank.exception.BusinessException;
import com.mchtcavas.simplebank.exception.BusinessExceptionKey;
import com.mchtcavas.simplebank.pipe.TransactionPipe;

import java.util.List;

public class InvalidOperatorName implements CheckTransaction {
    private final List<String> operators;

    public InvalidOperatorName(ApplicationProperties applicationProperties) {
        this.operators = applicationProperties.getBillPayment().getOperators();
    }

    @Override
    public void validate(TransactionPipe pipe) {
        if (!this.operators.contains(pipe.getProviderName())) {
            throw new BusinessException(BusinessExceptionKey.INVALID_OPERATOR_NAME);
        }
    }
}
