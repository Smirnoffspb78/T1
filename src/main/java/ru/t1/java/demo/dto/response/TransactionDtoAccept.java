package ru.t1.java.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransactionDtoAccept {
    private UUID clientId;
    private UUID accountId;
    private UUID transactionId;
    private LocalDateTime transactionTime;
    private BigDecimal amount;
    private BigDecimal balance;
}
