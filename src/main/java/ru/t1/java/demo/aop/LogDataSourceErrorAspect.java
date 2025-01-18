package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.service.LogErrorService;
import ru.t1.java.demo.util.ExtractStack;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LogDataSourceErrorAspect {

    private final LogErrorService logErrorService;

    @AfterThrowing(value = "@annotation(ru.t1.java.demo.annotation.LogDataSourceError)", throwing = "ex")
    public void logError(JoinPoint joinPoint, Throwable ex) {
        try {
            log.error(ex.getMessage());
            log.error(ExtractStack.getStackTraceAsString(ex));
            logErrorService.logError(ex, joinPoint);
        } catch (Throwable e) {
            log.error(e.getMessage());
            log.error(ExtractStack.getStackTraceAsString(e));
        }
    }
}