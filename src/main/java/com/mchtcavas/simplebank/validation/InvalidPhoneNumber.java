package com.mchtcavas.simplebank.validation;

import com.mchtcavas.simplebank.exception.BusinessException;
import com.mchtcavas.simplebank.exception.BusinessExceptionKey;
import com.mchtcavas.simplebank.pipe.TransactionPipe;

import java.util.regex.Pattern;

public class InvalidPhoneNumber implements CheckTransaction {
    private static final String TURKEY_PHONE_REGEX = "^0(5[0-9]{2})[0-9]{7}$";
    @Override
    public void validate(TransactionPipe pipe) {
        Pattern pattern = Pattern.compile(TURKEY_PHONE_REGEX);

        if (!pattern.matcher(pipe.getBillNumber()).matches()) {
            throw new BusinessException(BusinessExceptionKey.INVALID_PHONE_NUMBER);
        }
    }
}
