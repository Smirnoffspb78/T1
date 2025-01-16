package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;

import static ru.t1.java.demo.util.ExtractStack.getStackTraceAsString;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class LogDataSourceErrorAspect {

    private final DataSourceErrorLogRepository errorLogRepository;

    @Around("@annotation(ru.t1.java.demo.annotation.LogDataSourceError) || @within(ru.t1.java.demo.annotation.LogDataSourceError)")
    public Object logError(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            final Throwable methodException = ex;
            try {
                final DataSourceErrorLog errorLog = new DataSourceErrorLog();
                errorLog.setMessage(ex.getMessage());
                errorLog.setStackTrace(getStackTraceAsString(methodException));
                errorLog.setMethodSignature(joinPoint.getSignature().toShortString());
                errorLogRepository.save(errorLog);
            } catch (Throwable loggingException) {
                log.error("Log DataSourceError is failed: {}", loggingException.getMessage());
                loggingException.printStackTrace();
            } finally {
                throw methodException;
            }
        }
    }
}