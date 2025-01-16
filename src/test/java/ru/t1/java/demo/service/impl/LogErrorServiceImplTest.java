package ru.t1.java.demo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;


@ExtendWith(MockitoExtension.class)
class LogErrorServiceImplTest {
    @Mock
    private DataSourceErrorLogRepository dataSourceErrorLogRepository;
    @InjectMocks
    private LogErrorServiceImpl logErrorService;

    @Test
    void logError() {
    }
}