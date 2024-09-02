package com.mchtcavas.simplebank.validation;

import com.mchtcavas.simplebank.pipe.TransactionPipe;

import java.util.ArrayList;
import java.util.List;

public class ValidationChain {
    private final List<CheckTransaction> validations = new ArrayList<>();

    public void addValidation(CheckTransaction validation) {
        validations.add(validation);
    }

    public void validate(TransactionPipe pipe) {
        for (CheckTransaction validation : validations) {
            validation.validate(pipe);
        }
    }
}
