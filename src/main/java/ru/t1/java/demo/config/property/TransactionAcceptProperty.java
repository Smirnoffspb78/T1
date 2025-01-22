package ru.t1.java.demo.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @param transactionAcceptTopic Топик для транзакции на рассмотрение
 * @param transactionsHeader Заголовки топика для транзакции на рассмотрение
 */
@ConfigurationProperties(prefix = "kafka.message")
public record TransactionAcceptProperty(
        String transactionAcceptTopic,
        String transactionsHeader,
        String transactionResultTopic
) {
}
