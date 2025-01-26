package ru.t1.java.demo.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @param logErrorTopic  Наименование топика для сообщений об исключениях.
 * @param logErrorHeader Заголовок для отправки сообщений об исключениях.
 * @param timeOut        Время ожидания отправки сообщения в kafka.
 */
@ConfigurationProperties(prefix = "kafka.message")
public record LogDataSourceProperty(
        String logErrorTopic,
        String logErrorHeader,
        long timeOut
) {
}
