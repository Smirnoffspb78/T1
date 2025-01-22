package ru.t1.java.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka.config.producer")
@Getter
@Setter
public class KafkaProducerProperties {

    /**
     * Серверы для kafka
     */
    @Value("${kafka.config.bootstrapServer}")
    private String bootstrapServer;

    /**
     * Стратегия подтверждения отправки сообщения
     */
    private String acks;

    /**
     * Количество попыток повторной записи.
     */
    private String retry;

    /**
     * Время ожидания ответа Kafka до выброса исключения.
     */
    private String timeout;

    /**
     * Отключение возможности записи двух одинаковых сообщений в Kafka.
     */
    private String idempotence;
}
