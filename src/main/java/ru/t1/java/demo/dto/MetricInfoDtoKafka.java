package ru.t1.java.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;


@Builder
@AllArgsConstructor(access = PRIVATE)
@ToString
@NoArgsConstructor
@Getter
@Setter
public class MetricInfoDtoKafka implements Serializable {
    /**
     * Время работы метода.
     */
    private long time;
    /**
     * Имя метода.
     */
    private String methodName;

    /**
     * Список параметров метода.
     */
    private List<String> parametersMethod;
}
