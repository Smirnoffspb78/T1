package ru.t1.java.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogDataSourceErrorAspectTest {
    @InjectMocks
    private LogDataSourceErrorAspect logDataSourceErrorAspect;
    @Mock
    private DataSourceErrorLogRepository errorLogRepository;
    @Mock
    private ProceedingJoinPoint joinPoint;
    @Mock
    private Signature signature;

    @Test
    void logError_NoException() throws Throwable {
        final var variable = "Some object";
        when(joinPoint.proceed()).thenReturn(variable);

        Object result = logDataSourceErrorAspect.logError(joinPoint);

        assertEquals(variable, result);
        verify(errorLogRepository, never()).save(any(DataSourceErrorLog.class));
    }

    @Test
    void testLogError_ExceptionNotDBWrite() throws Throwable {
        final Throwable testException = new NullPointerException();

        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.proceed()).thenThrow(testException);

        assertThrows(NullPointerException.class, () -> logDataSourceErrorAspect.logError(joinPoint));

        verify(errorLogRepository).save(any(DataSourceErrorLog.class));
    }

    @Test
    void testLogError_ExceptionDBWrite() throws Throwable {
        final Throwable testException = new NullPointerException();

        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.proceed()).thenThrow(testException);

        doThrow(new IllegalAccessError()).when(errorLogRepository).save(any(DataSourceErrorLog.class));

        assertThrows(NullPointerException.class, () -> logDataSourceErrorAspect.logError(joinPoint));

        verify(errorLogRepository).save(any(DataSourceErrorLog.class));
    }
}