package ru.t1.java.demo.service.handler;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.response.TransactionDtoResult;
import ru.t1.java.demo.model.enums.TransactionStatus;
import ru.t1.java.demo.service.impl.TransactionServiceImpl;

import static ru.t1.java.demo.model.enums.TransactionStatus.REJECTED;

@Component
public class RejectedStatusHandler extends TransactionStatusHandler {
    public RejectedStatusHandler(@Lazy TransactionServiceImpl transactionService) {
        super(transactionService);
    }

    @Override
    public void handleStatus(TransactionDtoResult transactionDtoResult) {
        transactionService.rejectedTransaction(transactionDtoResult);
    }

    @Override
    public TransactionStatus getStatus() {
        return REJECTED;
    }
}
