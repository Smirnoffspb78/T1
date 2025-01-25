package ru.t1.java.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.dto.response.DataSourceErrorLogDto;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;
import ru.t1.java.demo.service.DataSourceErrorService;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * Сервисный слой для работы с исключениями.
 */
@Service
@RequiredArgsConstructor
public class DataSourceErrorServiceImpl implements DataSourceErrorService {

    private final DataSourceErrorLogRepository dataSourceErrorLogRepository;

    private final ModelMapper modelMapper;

    @Transactional(propagation = REQUIRES_NEW)
    @Override
    public void save(DataSourceErrorLogDto dataSourceErrorLog) {
        final DataSourceErrorLog dataSourceErrorLog1 = modelMapper.map(dataSourceErrorLog, DataSourceErrorLog.class);
        dataSourceErrorLogRepository.save(dataSourceErrorLog1);
    }
}
