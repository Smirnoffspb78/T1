package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.annotation.Metric;
import ru.t1.java.demo.config.property.MetricProperty;
import ru.t1.java.demo.dto.MetricInfoDtoKafka;
import ru.t1.java.demo.out.KafkaProducerService;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@Slf4j
@Component
@Aspect
@Order(2)
@RequiredArgsConstructor
public class MetricAspect {

    private final KafkaProducerService kafkaProducerService;

    private final MetricProperty metricProperty;

    @Around(value = "@annotation(ru.t1.java.demo.annotation.Metric)")
    public Object logRequestProcessing(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = currentTimeMillis();
        Object resultMethod = joinPoint.proceed();
        long endTime = currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("Request execution time - {} ms", resultTime);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if (resultTime > methodSignature.getMethod().getAnnotation(Metric.class).validTime()) {
            List<String> parameters = Arrays.stream(joinPoint.getArgs())
                    .map(object -> object.getClass().getSimpleName() + " = " + object)
                    .toList();
            MetricInfoDtoKafka metricInfoDtoKafka = MetricInfoDtoKafka.builder()
                    .time(resultTime)
                    .methodName(joinPoint.getSignature().getName())
                    .parametersMethod(parameters)
                    .build();
            kafkaProducerService.sendMessage(metricProperty.getMetricTopic(), "errorType",
                    metricProperty.getMetricHeader(), metricInfoDtoKafka);
            log.info("information metrics: {}", metricInfoDtoKafka);
        }
        return resultMethod;
    }
}
