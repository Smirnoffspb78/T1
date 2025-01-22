package ru.t1.java.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.t1.java.demo.dto.request.TransactionDtoRequest;
import ru.t1.java.demo.dto.response.TransactionDtoAccept;
import ru.t1.java.demo.dto.response.TransactionDtoResponse;
import ru.t1.java.demo.dto.response.TransactionDtoResult;

/**
 * Сервисный слой для работы с транзакциями.
 */
public interface TransactionService {
    /**
     * Возвращает страницу с банковскими транзакциями.
     * @param pageable Страница с ее параметрами
     * @return Страница с банковскими транзакциями
     */
    Page<TransactionDtoResponse> getAllTransactions(Pageable pageable);

    /**
     * Возвращает транзакцию по ее идентификатору.
     * @param id Идентификатор транзакции
     * @return Информация о банковском счете
     */
    TransactionDtoResponse getTransaction(Long id);

    /**
     * Сохраняет транзакцию в БД
     * @param transactionDtoRequest Dto транзакции
     * @return Идентификатор транзакции
     */
    Long saveTransaction(TransactionDtoRequest transactionDtoRequest);

    /**
     * Выполняет кредитовую операцию.
     *
     * @param transactionDtoRequest Dto транзакции
     * @return
     */
    TransactionDtoAccept rejectTransactionOperation(TransactionDtoRequest transactionDtoRequest);

    void handleResultTransaction(TransactionDtoResult transactionDtoResult);
}
