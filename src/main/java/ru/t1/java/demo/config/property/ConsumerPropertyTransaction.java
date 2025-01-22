package ru.t1.java.demo.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @param bootstrapServer    Серверы для kafka.
 * @param groupIdTransaction Идентификатор группы для прослушивания транзакций.
 * @param transactionPath    Путь к объекту транзакций.
 * @param offsetReset        Указывает на то, как читать сообщения в случае сброса указателя.
 * @param autoCommit         Нужно ли делать автокоммит после прочтения сообщения.
 * @param isolation          Уровень изоляции.
 */
@ConfigurationProperties(prefix = "kafka.config.consumer.transaction")
public record ConsumerPropertyTransaction(
        String bootstrapServer,
        String groupIdTransaction,
        String transactionPath,
        String offsetReset,
        boolean autoCommit,
        String isolation
) {
}
