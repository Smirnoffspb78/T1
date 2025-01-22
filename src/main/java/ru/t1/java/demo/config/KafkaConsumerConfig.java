package ru.t1.java.demo.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.t1.java.demo.dto.request.AccountDtoRequest;
import ru.t1.java.demo.dto.request.TransactionDtoRequest;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaConsumerProperties kafkaConsumerProperties;

    @Bean
    public ConsumerFactory<String, AccountDtoRequest> consumerFactoryAccount() {
        final Map<String, Object> configFactory = new HashMap<>();
        configFactory.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerProperties.getBootstrapServer());
        configFactory.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerProperties.getGroupIdAccount());
        configFactory.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configFactory.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configFactory.put(JsonDeserializer.TRUSTED_PACKAGES, kafkaConsumerProperties.getReadPackage());
        configFactory.put(JsonDeserializer.VALUE_DEFAULT_TYPE, kafkaConsumerProperties.getAccountPath());
        configFactory.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, kafkaConsumerProperties.isInfoHeaders());
        configFactory.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerProperties.getOffsetReset());
        configFactory.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConsumerProperties.isAutoCommit());

        return new DefaultKafkaConsumerFactory<>(configFactory);
    }

    @Bean
    public ConsumerFactory<String, TransactionDtoRequest> consumerFactoryTransaction() {
        final Map<String, Object> configFactory = new HashMap<>();
        configFactory.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerProperties.getBootstrapServer());
        configFactory.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerProperties.getGroupIdTransaction());
        configFactory.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configFactory.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configFactory.put(JsonDeserializer.TRUSTED_PACKAGES, kafkaConsumerProperties.getReadPackage());
        configFactory.put(JsonDeserializer.VALUE_DEFAULT_TYPE, kafkaConsumerProperties.getTransactionPath());
        configFactory.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, kafkaConsumerProperties.isInfoHeaders());
        configFactory.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerProperties.getOffsetReset());
        configFactory.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConsumerProperties.isAutoCommit());

        return new DefaultKafkaConsumerFactory<>(configFactory);
    }

    @Bean("listenerFactoryAccount")
    public ConcurrentKafkaListenerContainerFactory<String, AccountDtoRequest> kafkaListenerFactoryAccount() {
        final ConcurrentKafkaListenerContainerFactory<String, AccountDtoRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryAccount());
        return factory;
    }

    @Bean("listenerFactoryTransaction")
    public ConcurrentKafkaListenerContainerFactory<String, TransactionDtoRequest> kafkaListenerFactoryTransaction() {
        final ConcurrentKafkaListenerContainerFactory<String, TransactionDtoRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryTransaction());
        return factory;
    }
}