package ru.t1.java.demo.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String headerKey, String header, Object payload) {
        Message<Object> kafkaMessage = MessageBuilder
                .withPayload(payload)
                .setHeader(TOPIC, topic)
                .setHeader(headerKey, header)
                .build();

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaMessage);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send message to Kafka topic {}: {}", topic, payload, ex);
            } else {
                log.info("Message successfully sent to Kafka topic {}:", topic);
                log.info("Message sent with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}