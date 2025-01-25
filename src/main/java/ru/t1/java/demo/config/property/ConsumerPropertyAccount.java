package ru.t1.java.demo.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.config.consumer.account")
@Getter
@Setter
public class ConsumerPropertyAccount {

    /**
     * Серверы для kafka.
     */
    private String bootstrapServer;

    /**
     * Идентификатор группы для прослушивания банковских счетов.
     */
    private String groupIdAccount;
    /**
     * Путь к объекту для банковских счетов.
     */
    private String accountPath;

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
