package com.mchtcavas.simplebank.validation;

import com.mchtcavas.simplebank.exception.BusinessException;
import com.mchtcavas.simplebank.exception.BusinessExceptionKey;
import com.mchtcavas.simplebank.pipe.TransactionPipe;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidationManager {
    private final Map<TransactionPipe, ValidationChain> operationValidations = new HashMap<>();

    public void setValidationChain(TransactionPipe pipe, ValidationChain chain) {
        operationValidations.put(pipe, chain);
    }

    public void validateOperation(TransactionPipe pipe) {
        ValidationChain chain = this.operationValidations.get(pipe);

        if (chain == null) {
            throw new BusinessException(BusinessExceptionKey.VALIDATION_CHAIN_NOT_INITIALIZED);
        }

        chain.validate(pipe);
    }
}
