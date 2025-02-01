package ru.t1.java.demo.service.handler;

import lombok.RequiredArgsConstructor;
import ru.t1.java.demo.dto.response.TransactionDtoResult;
import ru.t1.java.demo.model.enums.TransactionStatus;
import ru.t1.java.demo.service.impl.TransactionServiceImpl;

@RequiredArgsConstructor
public abstract class TransactionStatusHandler {

    protected final TransactionServiceImpl transactionService;

    public abstract void handleStatus(TransactionDtoResult transactionDtoResult);

    public abstract TransactionStatus getStatus();
}
