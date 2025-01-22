package ru.t1.java.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDtoKafkaResponse {
    private Long clientId;

    private Long accountId;

    private Long transactionId;

    private Long timeStampId;

    private LocalDateTime time; //todo timestamp???

    private BigDecimal amount;

    private BigDecimal balance;
}
