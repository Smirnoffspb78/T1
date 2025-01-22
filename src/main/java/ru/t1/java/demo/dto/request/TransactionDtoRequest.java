package ru.t1.java.demo.dto.request;


import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDtoRequest(Long accountId,
                                    @Positive BigDecimal amount,
                                    @PastOrPresent LocalDateTime transactionTime) {
}
