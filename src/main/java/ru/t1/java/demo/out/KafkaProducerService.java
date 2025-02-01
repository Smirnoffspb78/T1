package ru.t1.java.demo.out;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static org.springframework.kafka.support.KafkaHeaders.KEY;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CompletableFuture<SendResult<String, String>> sendMessage(String topic, String headerKey, String header, Object payload, String key) {
        Message<Object> kafkaMessage = MessageBuilder
                .withPayload(payload)
                .setHeader(TOPIC, topic)
                .setHeader(headerKey, header)
                .setHeader(KEY, key)
                .build();
        return kafkaTemplate.executeInTransaction(operations -> operations.send(kafkaMessage));
    }
}