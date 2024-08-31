package com.mchtcavas.simplebank.exception;

public enum BusinessExceptionKey {
    ACCOUNT_NOT_FOUND("The specified account was not found."),
    INSUFFICIENT_FUNDS("The account has insufficient funds for the transaction."),
    ACCOUNT_ALREADY_EXISTS("An account with the given details already exists."),
    ACCOUNT_LIMIT_EXCEEDED("The transaction exceeds the allowed limit."),
    TRANSACTION_FAILED("The transaction could not be completed due to an error."),
    INVALID_TRANSACTION_AMOUNT("The transaction amount is invalid (e.g., negative or zero).");
    private final String defaultMessage;

    BusinessExceptionKey(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
