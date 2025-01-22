package ru.t1.java.demo.service;


import ru.t1.java.demo.model.DataSourceErrorLog;

public interface LogErrorService {

    /**
     * Логирует сообщение об ошибке
     *
     */
    void logError(DataSourceErrorLog dataSourceErrorLog);

    DataSourceErrorLog createDataSourceErrorLog(Throwable throwable, String methodSignature);
}
