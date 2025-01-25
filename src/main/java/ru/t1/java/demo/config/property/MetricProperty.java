package ru.t1.java.demo.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.message")
@Getter
@Setter
public class MetricProperty {

    private String metricTopic;

    private String metricHeader;
}
