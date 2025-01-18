package ru.t1.java.demo.service;

import org.aspectj.lang.JoinPoint;

public interface LogErrorService {

    /**
     * Логирует сообщение об ошибке
     *
     * @param throwable Исключение
     * @param joinPoint Точка присоединения
     * @return Идентификатор созданного объекта исключения
     */
    Long logError(Throwable throwable, JoinPoint joinPoint);
}
