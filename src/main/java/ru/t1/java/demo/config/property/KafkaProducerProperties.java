package ru.t1.java.demo.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.config.producer")
@Getter
@Setter
public class KafkaProducerProperties {

    /**
     * Серверы для kafka
     */
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
