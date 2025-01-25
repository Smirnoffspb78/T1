package ru.t1.java.demo.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.message")
@Getter
@Setter
public class LogDataSourceProperty {

    /**
     * Наименование топика для сообщений об исключениях.
     */
    private String logErrorTopic;

    /**
     * Заголовок для отправки сообщений об исключениях.
     */
    private String logErrorHeader;

    /**
     * Время ожидания отправки сообщения в kafka.
     */
    private long timeOut;
}
