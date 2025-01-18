package ru.t1.java.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;
import ru.t1.java.demo.service.LogErrorService;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static ru.t1.java.demo.util.ExtractStack.getStackTraceAsString;

/**
 * Сервисный слой для работы с исключениями.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LogErrorServiceImpl implements LogErrorService {

    private final DataSourceErrorLogRepository dataSourceErrorLogRepository;

    @Transactional(propagation = REQUIRES_NEW)
    @Override
    public Long logError(Throwable throwable, JoinPoint joinPoint) {
        final DataSourceErrorLog errorLog = new DataSourceErrorLog();
        final String stackTrace = getStackTraceAsString(throwable);
        errorLog.setMessage(throwable.getMessage());
        errorLog.setStackTrace(stackTrace);
        errorLog.setMethodSignature(joinPoint.getSignature().toShortString());
        return dataSourceErrorLogRepository
                .save(errorLog)
                .getId();
    }
}
