package ru.t1.java.demo.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.t1.java.demo.config.property.KafkaProducerProperties;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProducerProperties kafkaProducerProperties;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configFactory = new ConcurrentHashMap<>();
        configFactory.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerProperties.bootstrapServer());
        configFactory.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configFactory.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configFactory.put(ProducerConfig.ACKS_CONFIG, kafkaProducerProperties.acks());
        configFactory.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "metric-" + UUID.randomUUID());
        configFactory.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerProperties.timeout());
        configFactory.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerProperties.retry());
        configFactory.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, kafkaProducerProperties.idempotence());

        configFactory.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerProperties.lingerTimeout());
        configFactory.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, kafkaProducerProperties.transactionTimeout());
        configFactory.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, kafkaProducerProperties.deliveryTimeout());

        return new DefaultKafkaProducerFactory<>(configFactory);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}