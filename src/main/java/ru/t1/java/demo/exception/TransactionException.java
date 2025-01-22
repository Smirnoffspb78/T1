package ru.t1.java.demo.exception;

public class TransactionException extends RuntimeException{
    private static final String MESSAGE = "Account with id %s not have status OPEN";

    public TransactionException(Long accountId) {
        super(MESSAGE.formatted(accountId));
    }
}
