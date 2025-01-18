package ru.t1.java.demo.service.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class LogErrorServiceImplTest {
    @Mock
    private DataSourceErrorLogRepository dataSourceErrorLogRepository;
    @InjectMocks
    private LogErrorServiceImpl logErrorService;

    @Test
    void logError() {
        final Throwable throwable = new RuntimeException("Test exception");
        final JoinPoint joinPoint = mock(JoinPoint.class);
        final Signature signature = mock(Signature.class);
        final DataSourceErrorLog savedLog = mock(DataSourceErrorLog.class);
        final Long id = 1L;

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn("testMethod()");
        when(savedLog.getId()).thenReturn(1L);
        when(dataSourceErrorLogRepository.save(any(DataSourceErrorLog.class))).thenReturn(savedLog);

        final Long result = logErrorService.logError(throwable, joinPoint);

        assertNotNull(result);
        assertEquals(id, result);
    }
}