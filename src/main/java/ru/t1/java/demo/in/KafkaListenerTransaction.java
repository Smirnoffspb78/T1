package ru.t1.java.demo.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.config.property.TransactionAcceptProperty;
import ru.t1.java.demo.dto.request.TransactionDtoRequest;
import ru.t1.java.demo.dto.response.TransactionDtoAccept;
import ru.t1.java.demo.dto.response.TransactionDtoResult;
import ru.t1.java.demo.out.KafkaProducerService;
import ru.t1.java.demo.service.TransactionService;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListenerTransaction {

    private final TransactionService transactionService;

    private final KafkaProducerService kafkaProducerService;

    private final TransactionAcceptProperty transactionAcceptProperty;

 /*   @KafkaListener(topics = "${kafka.message.transactionTopic}", groupId = "${kafka.config.consumer.transaction.groupIdTransaction}",
            containerFactory = "listenerFactoryTransaction")
    public void listenTransaction(@Payload TransactionDtoRequest transactionDtoRequest, Acknowledgment acknowledgment) {
        log.info("Received message: {}", transactionDtoRequest);
        transactionService.saveTransaction(transactionDtoRequest);
        acknowledgment.acknowledge();
    }*/

    /**
     * Принимает транзакцию на рассмотрение, проверяет открыт ли счет. В случае успеха - отправляет транзакцию в сервис 2 для обработки.
     * @param transactionDtoRequest Dto транзакции
     * @param acknowledgment
     */
    @KafkaListener(topics = "${kafka.message.transactionTopic}", groupId = "${kafka.config.consumer.transaction.groupIdTransaction}",
            containerFactory = "listenerFactoryTransaction")
    public void listenTransactionReject(@Valid @Payload TransactionDtoRequest transactionDtoRequest, Acknowledgment acknowledgment) {
        log.info("Received message: {}", transactionDtoRequest);
        acknowledgment.acknowledge();
        TransactionDtoAccept transactionDtoAccept = transactionService.rejectTransactionOperation(transactionDtoRequest);
        kafkaProducerService.sendMessage(transactionAcceptProperty.transactionAcceptTopic(), "contentType",
                transactionAcceptProperty.transactionsHeader(), transactionDtoAccept);
    }

    @KafkaListener(topics = "${kafka.message.transactionResultTopic}", groupId = "${kafka.config.consumer.transaction.groupIdTransaction}",
            containerFactory = "listenerFactoryTransactionResult")
    public void listenTransactionResult(@Payload TransactionDtoResult transactionDtoResult, Acknowledgment acknowledgment) {
        log.info("Received message: {}", transactionDtoResult);
        acknowledgment.acknowledge();
        transactionService.handleResultTransaction(transactionDtoResult);
    }
}
