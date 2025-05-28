package com.zenika.vendingmachine.enums;

/**
 * Enum representing the status of a transaction
 */
public enum TransactionStatus {
    IN_PROGRESS("Transaction is in progress"),
    COMPLETED("Transaction completed successfully"),
    CANCELLED("Transaction was cancelled"),
    FAILED("Transaction failed");

    private final String description;

    TransactionStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}