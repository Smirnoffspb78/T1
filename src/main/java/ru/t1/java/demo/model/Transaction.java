package ru.t1.java.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractPersistable;
import ru.t1.java.demo.annotation.NonZeroAndNotNull;
import ru.t1.java.demo.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

/**
 * Транзакция
 * <p>{@link #account} - Банковский счет</p>
 * <p>{@link #amount} - Сумма транзакции</p>
 * <p>{@link #transactionTime} - Дата и время совершения транзакции</p>
 * <p>{@link #transactionStatus} - Статус транзакции</p>
 * <p>{@link #transactionId} - Уникальный идентификатор транзакции</p>
 */
@Getter
@Setter
@Entity
@Table(name = "transactions")
@ToString
public class Transaction extends AbstractPersistable<Long> {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="account_id")
    @NotNull
    private Account account;

    @Column(name = "amount")
    @NonZeroAndNotNull
    private BigDecimal amount;

    @PastOrPresent
    @NotNull
    @Column(name = "time")
    private LocalDateTime transactionTime;

    @NotNull
    @Column(name = "transaction_status")
    @Enumerated(value = STRING)
    private TransactionStatus transactionStatus;

    @NotNull
    @Column(name = "transaction_id")
    private UUID transactionId;
}