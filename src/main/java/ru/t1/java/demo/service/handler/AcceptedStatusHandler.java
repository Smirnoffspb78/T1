package ru.t1.java.demo.service.handler;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.response.TransactionDtoResult;
import ru.t1.java.demo.model.enums.TransactionStatus;
import ru.t1.java.demo.service.impl.TransactionServiceImpl;

import static ru.t1.java.demo.model.enums.TransactionStatus.ACCEPTED;

@Component
public class AcceptedStatusHandler extends TransactionStatusHandler {

    public AcceptedStatusHandler(@Lazy TransactionServiceImpl transactionService) {
        super(transactionService);
    }

    @Override
    public void handleStatus(TransactionDtoResult transactionDtoResult) {
        transactionService.acceptTransaction(transactionDtoResult);
    }

    @Override
    public TransactionStatus getStatus() {
        return ACCEPTED;
    }
}
