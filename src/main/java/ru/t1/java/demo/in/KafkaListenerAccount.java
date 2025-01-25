package ru.t1.java.demo.in;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.request.AccountDtoRequest;
import ru.t1.java.demo.service.AccountService;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListenerAccount {
    private final AccountService accountService;

    @KafkaListener(topics = "${kafka.message.accountTopic}", groupId = "${kafka.config.consumer.account.groupIdAccount}",
            containerFactory = "listenerFactoryAccount")
    public void listenAccount(@Payload AccountDtoRequest accountDtoRequest, Acknowledgment acknowledgment) {
        log.info("Received message: {}", accountDtoRequest);
        accountService.createAccount(accountDtoRequest);
        acknowledgment.acknowledge();
    }
}
