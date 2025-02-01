package ru.t1.java.demo.dto.response;

import jakarta.validation.constraints.NotNull;
import ru.t1.java.demo.annotation.NonZeroAndNotNull;
import ru.t1.java.demo.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record TransactionDtoResult(
        @NotNull
        UUID accountId,
        @NotNull
        UUID transactionId,
        @NotNull
        TransactionStatus transactionStatus,
        @NonZeroAndNotNull
        BigDecimal amount,
        @NotNull
        List<UUID> transactionsBlocked
) {

}
