package ru.t1.java.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.t1.java.demo.annotation.NonZeroAndNotNull;

import java.math.BigDecimal;

public class NonZeroValidator implements ConstraintValidator<NonZeroAndNotNull, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return !value.equals(BigDecimal.ZERO);
    }
}