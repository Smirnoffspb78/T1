package ru.t1.java.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.t1.java.demo.dto.request.AccountDtoRequest;
import ru.t1.java.demo.dto.response.AccountDtoResponse;
import ru.t1.java.demo.model.Account;

import java.util.UUID;


/**
 * Сервисный слой для работы с банковскими счетами.
 */
public interface AccountService {
    /**
     * Возвращает страницу с банковскими счетами.
     * @param pageable Страница с ее параметрами
     * @return Страница с банковскими счетами
     */
    Page<AccountDtoResponse>  getAllAccounts(Pageable pageable);

    /**
     * Возвращает банковский счет по его идентификатору.
     * @param id Идентификатор банковского счета
     * @return Информация о банковском счете
     */
    AccountDtoResponse getAccount(Long id);

    /**
     * Создает новый банковский счет клиента.
     */
    Long createAccount(AccountDtoRequest accountDtoRequest);

    /**
     * Возвращает банковский счет по его идентификатору.
     * @param id Идентификатор банковского счета
     * @return Информация о банковском счете
     */
    Account getAccountById(Long id);

    Account getAccountByAccountId(UUID accountId);
}
