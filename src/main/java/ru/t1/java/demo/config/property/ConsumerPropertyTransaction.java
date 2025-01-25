package ru.t1.java.demo.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.config.consumer.transaction")
@Getter
@Setter
public class ConsumerPropertyTransaction {
    /**
     * Серверы для kafka.
     */
    private String bootstrapServer;

    /**
     * Идентификатор группы для прослушивания транзакций.
     */
    private String groupIdTransaction;

    /**
     * Путь к объекту транзакций.
     */
    private String transactionPath;

    /**
     * Указывает на то, как читать сообщения в случае сброса указателя.
     */
    private String offsetReset;

    /**
     * Нужно ли делать автокоммит после прочтения сообщения.
     */
    private boolean autoCommit;

    /**
     * Уровень изоляции.
     */
    private String isolation;

}
