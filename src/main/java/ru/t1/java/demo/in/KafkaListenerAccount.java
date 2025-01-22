package ru.t1.java.demo.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.t1.java.demo.dto.request.AccountDtoRequest;
import ru.t1.java.demo.service.AccountService;

@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class KafkaListenerAccount {
    private final AccountService accountService;

    @KafkaListener(topics = "${kafka.message.accountTopic}", groupId = "${kafka.config.consumer.account.groupIdAccount}",
            containerFactory = "listenerFactoryAccount")
    public void listenAccount(@Valid @Payload AccountDtoRequest accountDtoRequest, Acknowledgment acknowledgment) {
        log.info("Received message: {}", accountDtoRequest);
        accountService.createAccount(accountDtoRequest);
        acknowledgment.acknowledge();
    }
}
