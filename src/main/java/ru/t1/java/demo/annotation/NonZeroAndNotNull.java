package ru.t1.java.demo.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.t1.java.demo.validation.NonZeroValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NonZeroValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NonZeroAndNotNull {

    String message() default "Значение не должно быть равно 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}