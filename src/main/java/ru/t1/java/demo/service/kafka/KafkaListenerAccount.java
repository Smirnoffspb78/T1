package ru.t1.java.demo.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.request.AccountDtoRequest;
import ru.t1.java.demo.service.AccountService;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListenerAccount {
    private final AccountService accountService;

    @KafkaListener(topics = "t1_demo_accounts", groupId = "accountGroup",
            containerFactory = "listenerFactoryAccount")
    public void listenAccount(@Payload AccountDtoRequest accountDtoRequest) {
        log.info("Received message: {}", accountDtoRequest);
        accountService.createAccount(accountDtoRequest);
    }
}
