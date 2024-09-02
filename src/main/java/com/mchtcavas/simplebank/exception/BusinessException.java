package com.mchtcavas.simplebank.exception;

public class BusinessException extends RuntimeException {
    private final BusinessExceptionKey businessExceptionKey;
    public BusinessException() {
        super();
        this.businessExceptionKey = null;
    }

    public BusinessException(BusinessExceptionKey businessExceptionKey) {
        super(businessExceptionKey.getDefaultMessage());
        this.businessExceptionKey = businessExceptionKey;
    }

    public BusinessException(BusinessExceptionKey businessExceptionKey, Throwable cause) {
        super(businessExceptionKey.getDefaultMessage(), cause);
        this.businessExceptionKey = businessExceptionKey;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.businessExceptionKey = null;
    }

    public BusinessExceptionKey getBusinessExceptionKey() {
        return businessExceptionKey;
    }
}
