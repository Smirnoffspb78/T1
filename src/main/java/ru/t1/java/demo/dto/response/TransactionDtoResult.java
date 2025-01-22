package ru.t1.java.demo.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.t1.java.demo.annotation.NonZeroAndNotNull;
import ru.t1.java.demo.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class TransactionDtoResult {
    @NotNull
    private UUID accountId;
    @NotNull
    private UUID transactionId;
    @NotNull
    private TransactionStatus transactionStatus;
    @NonZeroAndNotNull
    private BigDecimal amount;
    @NotNull
    private List<UUID> transactionsBlocked;
}
