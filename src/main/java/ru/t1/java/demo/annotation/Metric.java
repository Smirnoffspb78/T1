package ru.t1.java.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Маркерная аннотация отмечает, методы, время работы которых будет фиксироваться.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Metric {
    /**
     * Допустимое время выполнения, [мс]
     * @return Допустимое время выполнения
     */
    int validTime();
}
