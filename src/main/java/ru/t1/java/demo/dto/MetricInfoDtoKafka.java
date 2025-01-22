package ru.t1.java.demo.dto;

import java.util.List;


/**
 * @param time             Время работы метода.
 * @param methodName       Имя метода.
 * @param parametersMethod Список параметров метода.
 */
public record MetricInfoDtoKafka(
        long time,
        String methodName,
        List<String> parametersMethod) {
}
