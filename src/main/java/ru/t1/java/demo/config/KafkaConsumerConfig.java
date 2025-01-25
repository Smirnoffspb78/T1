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
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;
import ru.t1.java.demo.config.property.ConsumerPropertyAccount;
import ru.t1.java.demo.config.property.ConsumerPropertyTransaction;
import ru.t1.java.demo.dto.request.AccountDtoRequest;
import ru.t1.java.demo.dto.request.TransactionDtoRequest;

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
        final Map<String, Object> configFactory = new HashMap<>();
        configFactory.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerPropertyAccount.getBootstrapServer());
        configFactory.put(ConsumerConfig.GROUP_ID_CONFIG, consumerPropertyAccount.getGroupIdAccount());
        configFactory.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configFactory.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configFactory.put(JsonDeserializer.VALUE_DEFAULT_TYPE, consumerPropertyAccount.getAccountPath());
        configFactory.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerPropertyAccount.getOffsetReset());
        configFactory.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerPropertyAccount.isAutoCommit());
        configFactory.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, consumerPropertyAccount.getIsolation());

        return new DefaultKafkaConsumerFactory<>(configFactory);
    }

    @Bean
    public ConsumerFactory<String, TransactionDtoRequest> consumerFactoryTransaction() {
        final Map<String, Object> configFactory = new HashMap<>();
        configFactory.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerPropertyTransaction.getBootstrapServer());
        configFactory.put(ConsumerConfig.GROUP_ID_CONFIG, consumerPropertyTransaction.getGroupIdTransaction());
        configFactory.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configFactory.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configFactory.put(JsonDeserializer.VALUE_DEFAULT_TYPE, consumerPropertyTransaction.getTransactionPath());
        configFactory.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerPropertyTransaction.getOffsetReset());
        configFactory.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerPropertyTransaction.isAutoCommit());
        configFactory.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, consumerPropertyTransaction.getIsolation());

        return new DefaultKafkaConsumerFactory<>(configFactory);
    }

    @Bean("listenerFactoryAccount")
    public ConcurrentKafkaListenerContainerFactory<String, AccountDtoRequest> kafkaListenerFactoryAccount() {
        final ConcurrentKafkaListenerContainerFactory<String, AccountDtoRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryAccount());
        factory.setCommonErrorHandler(errorHandler());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        return factory;
    }

    @Bean("listenerFactoryTransaction")
    public ConcurrentKafkaListenerContainerFactory<String, TransactionDtoRequest> kafkaListenerFactoryTransaction() {
        final ConcurrentKafkaListenerContainerFactory<String, TransactionDtoRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryTransaction());
        factory.setCommonErrorHandler(errorHandler());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(2000, 3);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
            log.error("DefaultErrorHandler: {}", exception.getMessage());
        }, fixedBackOff);
        errorHandler.addNotRetryableExceptions(SerializationException.class);
        return errorHandler;
    }
}