package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.config.property.LogDataSourceProperty;
import ru.t1.java.demo.dto.response.DataSourceErrorLogDto;
import ru.t1.java.demo.service.DataSourceErrorService;
import ru.t1.java.demo.out.KafkaProducerService;

import static java.util.concurrent.TimeUnit.SECONDS;
import static ru.t1.java.demo.util.ExceptionUtil.createDataSourceErrorLog;
import static ru.t1.java.demo.util.ExceptionUtil.getStackTraceAsString;

@Aspect
@Component
@Slf4j
@Order(1)
@RequiredArgsConstructor
public class LogDataSourceErrorAspect {

    private final KafkaProducerService kafkaProducerService;

    private final DataSourceErrorService dataSourceErrorService;

    private final LogDataSourceProperty logDataSourceProperty;


    @AfterThrowing(value = "@annotation(ru.t1.java.demo.annotation.LogDataSourceError)", throwing = "ex")
    public void logError(JoinPoint joinPoint, Throwable ex) {
        logConsole(joinPoint, ex);
        DataSourceErrorLogDto dataSourceErrorLogDto = createDataSourceErrorLog(ex, joinPoint.getSignature().toShortString());
        try {
             kafkaProducerService.sendMessage(logDataSourceProperty.logErrorTopic(), "errorType",
                    logDataSourceProperty.logErrorHeader(), dataSourceErrorLogDto)
                    .get(logDataSourceProperty.timeOut(), SECONDS);
            log.info("Message successfully sent to Kafka topic {}:", logDataSourceProperty.logErrorTopic());
        } catch (Throwable eKafka) {
            try {
                dataSourceErrorService.save(dataSourceErrorLogDto);
            } catch (Throwable eDB){
                logConsole(joinPoint, eDB);
            }
        }
    }

    private void logConsole(JoinPoint joinPoint, Throwable ex){
        log.error("Error out method: {}. Error message: {}\n{}", joinPoint.getSignature(),  ex.getMessage(), getStackTraceAsString(ex));
    }
}