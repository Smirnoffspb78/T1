package ru.t1.java.demo.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @param metricTopic наименование топика для сбора метрик.
 * @param metricHeader Заголовки метрик.
 */
@ConfigurationProperties(prefix = "kafka.message")
public record MetricProperty(
        String metricTopic,
        String metricHeader
) {
}
