package com.ayush.expense_backend.exception;

public class NoOperationPerformedException extends RuntimeException {
    public NoOperationPerformedException(String message) {
        super(message);
    }
}
