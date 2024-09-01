package com.mchtcavas.simplebank.exception;

public enum BusinessExceptionKey {
    ACCOUNT_NOT_FOUND("The specified account was not found."),
    ACCOUNT_ALREADY_EXISTS("An account with the given details already exists."),
    INSUFFICIENT_FUNDS("The account has insufficient funds for the transaction."),
    INVALID_TRANSACTION_AMOUNT("The transaction amount is invalid (e.g., negative or zero)."),
    INVALID_OPERATOR_NAME("The specified operator name is invalid or unrecognized."),
    INVALID_PHONE_NUMBER("The specified phone number is invalid or does not meet the required format."),
    VALIDATION_CHAIN_NOT_INITIALIZED("Validation chain is not initialized.");
    private final String defaultMessage;

    BusinessExceptionKey(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
