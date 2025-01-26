package ru.t1.java.demo.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @param bootstrapServer Серверы для kafka.
 * @param groupIdAccount  Идентификатор группы для прослушивания банковских счетов.
 * @param accountPath     Путь к объекту для банковских счетов.
 * @param offsetReset     Указывает на то, как читать сообщения в случае сброса указателя.
 * @param autoCommit      Нужно ли делать автокоммит после прочтения сообщения.
 * @param isolation       Уровень изоляции.
 */
@ConfigurationProperties(prefix = "kafka.config.consumer.account")
public record ConsumerPropertyAccount(
        String bootstrapServer,
        String groupIdAccount,
        String accountPath,
        String offsetReset,
        boolean autoCommit,
        String isolation

) {
}
