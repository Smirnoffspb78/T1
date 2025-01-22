package ru.t1.java.demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;
import ru.t1.java.demo.config.property.ConsumerPropertyAccount;
import ru.t1.java.demo.config.property.ConsumerPropertyTransaction;
import ru.t1.java.demo.dto.request.AccountDtoRequest;
import ru.t1.java.demo.dto.request.TransactionDtoRequest;
import ru.t1.java.demo.dto.response.TransactionDtoResult;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerConfig {

    private final ConsumerPropertyAccount consumerPropertyAccount;

    private final ConsumerPropertyTransaction consumerPropertyTransaction;

    @Bean
    public ConsumerFactory<String, AccountDtoRequest> consumerFactoryAccount() {
        StringDeserializer keyDeserializer = new StringDeserializer();
        JsonDeserializer<AccountDtoRequest> jsonDeserializer = new JsonDeserializer<>(AccountDtoRequest.class);
        ErrorHandlingDeserializer<AccountDtoRequest> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(jsonDeserializer);

        final Map<String, Object> configFactory = new HashMap<>();
        configFactory.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerPropertyAccount.bootstrapServer());
        configFactory.put(ConsumerConfig.GROUP_ID_CONFIG, consumerPropertyAccount.groupIdAccount());
        configFactory.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configFactory.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configFactory.put(JsonDeserializer.VALUE_DEFAULT_TYPE, consumerPropertyAccount.accountPath());
        configFactory.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerPropertyAccount.offsetReset());
        configFactory.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerPropertyAccount.autoCommit());
        configFactory.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, consumerPropertyAccount.isolation());

        return new DefaultKafkaConsumerFactory<>(configFactory, keyDeserializer, errorHandlingDeserializer);
    }

    @Bean
    public ConsumerFactory<String, TransactionDtoRequest> consumerFactoryTransaction() {
        StringDeserializer keyDeserializer = new StringDeserializer();
        JsonDeserializer<TransactionDtoRequest> jsonDeserializer = new JsonDeserializer<>(TransactionDtoRequest.class);
        ErrorHandlingDeserializer<TransactionDtoRequest> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(jsonDeserializer);

        final Map<String, Object> configFactory = new HashMap<>();
        configFactory.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerPropertyTransaction.bootstrapServer());
        configFactory.put(ConsumerConfig.GROUP_ID_CONFIG, consumerPropertyTransaction.groupIdTransaction());
        configFactory.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configFactory.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configFactory.put(JsonDeserializer.VALUE_DEFAULT_TYPE, consumerPropertyTransaction.transactionPath());
        configFactory.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerPropertyTransaction.offsetReset());
        configFactory.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerPropertyTransaction.autoCommit());
        configFactory.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, consumerPropertyTransaction.isolation());

        return new DefaultKafkaConsumerFactory<>(configFactory, keyDeserializer, errorHandlingDeserializer);
    }

    @Bean
    public ConsumerFactory<String, TransactionDtoResult> consumerFactoryTransactionResult() {
        StringDeserializer keyDeserializer = new StringDeserializer();
        JsonDeserializer<TransactionDtoResult> jsonDeserializer = new JsonDeserializer<>(TransactionDtoResult.class);
        ErrorHandlingDeserializer<TransactionDtoResult> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(jsonDeserializer);

        final Map<String, Object> configFactory = new HashMap<>();
        configFactory.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerPropertyTransaction.bootstrapServer());
        configFactory.put(ConsumerConfig.GROUP_ID_CONFIG, consumerPropertyTransaction.groupIdTransaction());
        configFactory.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configFactory.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configFactory.put(JsonDeserializer.VALUE_DEFAULT_TYPE, consumerPropertyTransaction.transactionPathResult());
        configFactory.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerPropertyTransaction.offsetReset());
        configFactory.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerPropertyTransaction.autoCommit());
        configFactory.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, consumerPropertyTransaction.isolation());

        return new DefaultKafkaConsumerFactory<>(configFactory, keyDeserializer, errorHandlingDeserializer);
    }

    @Bean("listenerFactoryAccount")
    public ConcurrentKafkaListenerContainerFactory<String, AccountDtoRequest> kafkaListenerFactoryAccount() {
        final ConcurrentKafkaListenerContainerFactory<String, AccountDtoRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryAccount());
        factory.setCommonErrorHandler(errorHandler());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean("listenerFactoryTransaction")
    public ConcurrentKafkaListenerContainerFactory<String, TransactionDtoRequest> kafkaListenerFactoryTransaction() {
        final ConcurrentKafkaListenerContainerFactory<String, TransactionDtoRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryTransaction());
        factory.setCommonErrorHandler(errorHandler());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean("listenerFactoryTransactionResult")
    public ConcurrentKafkaListenerContainerFactory<String, TransactionDtoResult> kafkaListenerFactoryTransactionResult() {
        final ConcurrentKafkaListenerContainerFactory<String, TransactionDtoResult> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryTransactionResult());
        factory.setCommonErrorHandler(errorHandler());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(2000, 3);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
            log.error("DefaultErrorHandler: {}", exception.getMessage());
            exception.printStackTrace();
        }, fixedBackOff);
        errorHandler.addNotRetryableExceptions(SerializationException.class);
        return errorHandler;
    }
}