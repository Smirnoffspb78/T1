package ru.t1.java.demo.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @param bootstrapServer Серверы для kafka
 * @param acks            Стратегия подтверждения отправки сообщения
 * @param retry           Количество попыток повторной записи.
 * @param timeout         Время ожидания ответа Kafka до выброса исключения.
 * @param idempotence     Отключение возможности записи двух одинаковых сообщений в Kafka.
 */
@ConfigurationProperties(prefix = "kafka.config.producer")
public record KafkaProducerProperties(
        String bootstrapServer,
        String acks,
        String retry,
        String timeout,
        String idempotence,
        String lingerTimeout,
        String transactionTimeout,
        String deliveryTimeout
) {
}
