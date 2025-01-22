package ru.t1.java.demo.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.request.TransactionDtoRequest;
import ru.t1.java.demo.service.TransactionService;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListenerTransaction {

    private final TransactionService transactionService;

    @KafkaListener(topics = "t1_demo_transactions", groupId = "transactionGroup",
            containerFactory = "listenerFactoryTransaction")
    public void listenTransaction(@Payload TransactionDtoRequest transactionDtoRequest) {
        log.info("Received message: {}", transactionDtoRequest);
        transactionService.saveTransaction(transactionDtoRequest);
    }
}
