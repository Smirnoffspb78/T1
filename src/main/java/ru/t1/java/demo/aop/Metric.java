package ru.t1.java.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.MetricInfoDtoKafka;
import ru.t1.java.demo.service.kafka.KafkaProducerService;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@Slf4j
@Component
@Aspect
@Order(2)
public class Metric {

    /**
     * Допустимое время работы метода [мСек]
     */
    private final long validTime;
    /**
     * Имя топика.
     */
    private final String topicName;
    /**
     * Имя заголовка.
     */
    private final String headerName;

    private final KafkaProducerService kafkaProducerService;

    public Metric(@Value("${metric.validTime}") long validTime,
                  @Value("${kafka.topics.t1_demo_metrics}") String topicName,
                  @Value("${kafka.headers.metric}") String headerName,
                  KafkaProducerService kafkaProducerService
                  ) {
        if (validTime <= 0) {
            throw new IllegalArgumentException("validTime <= 0");
        }
        this.validTime = validTime;
        this.topicName = topicName;
        this.headerName = headerName;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Around(value = "@annotation(ru.t1.java.demo.annotation.Metric)")
    public Object logRequestProcessing(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = currentTimeMillis();
        Object resultMethod = joinPoint.proceed();
        long endTime = currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("Request execution time - {} ms", resultTime);

        if (resultTime > validTime) {
            List<String> parameters = Arrays.stream(joinPoint.getArgs()).map(object -> object.getClass().getName()).toList();
            MetricInfoDtoKafka metricInfoDtoKafka = MetricInfoDtoKafka.builder()
                    .time(resultTime)
                    .methodName(joinPoint.getSignature().getName())
                    .parametersMethod(parameters)
                    .build();
            kafkaProducerService.sendMessage(topicName, "errorType", headerName, metricInfoDtoKafka);
            log.info("information metrics: {}", metricInfoDtoKafka);
        }
        return resultMethod;
    }
}
