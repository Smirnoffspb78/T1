package ru.t1.java.demo.model.enums;

/**
 * Тип банковского счета.
 * <p>{@link #ACCEPTED} - Принята</p>
 * <p>{@link #REJECTED} - Отклонена</p>
 * <p>{@link #BLOCKED} - Заблокирована</p>
 * <p>{@link #CANCELLED} - Отменена</p>
 * <p>{@link #REQUESTED} - Запрошена</p>
 */
public enum TransactionStatus {
    ACCEPTED,
    REJECTED,
    BLOCKED,
    CANCELLED,
    REQUESTED
}
