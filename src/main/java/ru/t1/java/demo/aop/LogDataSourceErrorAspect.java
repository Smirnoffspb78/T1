package ru.t1.java.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.service.LogErrorService;
import ru.t1.java.demo.service.kafka.KafkaProducerService;

import static ru.t1.java.demo.util.ExtractStack.getStackTraceAsString;

@Aspect
@Component
@Slf4j
@Order(1)
public class LogDataSourceErrorAspect {

    private final KafkaProducerService kafkaProducerService;

    private final LogErrorService logErrorService;

    private final String topicLog;

    private final String headerLog;

    public LogDataSourceErrorAspect(KafkaProducerService kafkaProducerService, LogErrorService logErrorService,
                                    @Value("${kafka.topics.t1_demo_metrics}") String topicLog,
                                    @Value("${kafka.headers.logError}") String headerLog) {
        this.kafkaProducerService = kafkaProducerService;
        this.logErrorService = logErrorService;
        this.topicLog = topicLog;
        this.headerLog = headerLog;
    }

    @AfterThrowing(value = "@annotation(ru.t1.java.demo.annotation.LogDataSourceError)", throwing = "ex")
    public void logError(JoinPoint joinPoint, Throwable ex) {
        DataSourceErrorLog dataSourceErrorLog = null;
        try {
            logConsole(joinPoint, ex);
            dataSourceErrorLog = logErrorService.createDataSourceErrorLog(ex, joinPoint.getSignature().toShortString());
            kafkaProducerService.sendMessage(topicLog, "errorType", headerLog, dataSourceErrorLog);
        } catch (Throwable eKafka) {
            try {
                logErrorService.logError(dataSourceErrorLog);
            } catch (Throwable eDB){
                logConsole(joinPoint, eDB);
            }
        }
    }

    private void logConsole(JoinPoint joinPoint, Throwable ex){
        log.error("Error in method: {}. Error message: {}\n{}", joinPoint.getSignature(),  ex.getMessage(), getStackTraceAsString(ex));
    }
}