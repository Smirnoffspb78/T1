package ru.t1.java.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import ru.t1.java.demo.model.enums.AccountStatus;
import ru.t1.java.demo.model.enums.AccountType;

import java.math.BigDecimal;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;

/**
 * Банковски счет.
 * <p>{@link #client} - клиент банка</p>
 * <p>{@link #accountType} - тип банковского счета</p>
 * <p>{@link #balance} - баланс банковского счета</p>
 * <p>{@link #accountId} - уникальный идентификатор</p>
 * <p>{@link #frozenAmount} - замороженная сумма</p>
 */
@Entity
@Getter
@Setter
@Table(name = "accounts")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class Account extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull
    private Client client;

    @NotNull
    @Column(name = "account_type")
    @Enumerated(value = STRING)
    private AccountType accountType;

    @NotNull
    //@PositiveOrZero(groups = BalanceValidation.class)
    @Column(name = "balance")
    private BigDecimal balance;

    @NotNull
    @Column(name = "account_status")
    @Enumerated(value = STRING)
    private AccountStatus accountStatus;

    @NotNull
    @Column(name = "account_id")
    private UUID accountId;

    @NotNull
    //@PositiveOrZero
    @Column(name = "frozen_amount")
    private BigDecimal frozenAmount;
}
