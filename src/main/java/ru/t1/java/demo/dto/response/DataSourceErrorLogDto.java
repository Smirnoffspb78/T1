package ru.t1.java.demo.dto.response;

import jakarta.validation.constraints.NotBlank;


public record DataSourceErrorLogDto(@NotBlank String stackTrace,
                                    String message,
                                    @NotBlank String methodSignature) {
}
