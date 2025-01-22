package ru.t1.java.demo.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka.config.consumer")
@Getter
@Setter
public class KafkaConsumerProperties {

    /**
     * Серверы для kafka.
     */
    @Value("${kafka.config.bootstrapServer}")
    private String bootstrapServer;

    /**
     * Идентификатор группы для прослушивания банковских счетов.
     */
    private String groupIdAccount;

    /**
     * Идентификатор группы для прослушивания транзакций.
     */
    private String groupIdTransaction;

    /**
     * Пакет для чтения объектов.
     */
    private String readPackage;

    /**
     * Путь к объекту для банковских счетов.
     */
    private String accountPath;

    /**
     * Путь к объекту транзакций.
     */
    private String transactionPath;

    /**
     * Необходимо ли десериализовывать заголовки.
     */
    private boolean infoHeaders;

    /**
     * Указывает на то, как читать сообщения в случае сброса указателя.
     */
    private String offsetReset;

    /**
     * Нужно ли делать автокоммит после прочтения сообщения.
     */
    private boolean autoCommit;
}
