package ru.t1.java.demo.service;

import ru.t1.java.demo.dto.response.DataSourceErrorLogDto;

public interface DataSourceErrorService {

    /**
     * Логирует сообщение об ошибке
     *
     */
    void save(DataSourceErrorLogDto dataSourceErrorLog);
}
