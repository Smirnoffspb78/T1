package ru.t1.java.demo.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.annotation.LogDataSourceError;
import ru.t1.java.demo.dto.request.TransactionDtoRequest;
import ru.t1.java.demo.dto.response.TransactionDtoAccept;
import ru.t1.java.demo.dto.response.TransactionDtoResponse;
import ru.t1.java.demo.dto.response.TransactionDtoResult;
import ru.t1.java.demo.exception.EntityNotFoundException;
import ru.t1.java.demo.exception.TransactionException;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.model.enums.AccountStatus;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.AccountService;
import ru.t1.java.demo.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

import static java.util.UUID.randomUUID;
import static ru.t1.java.demo.model.enums.AccountStatus.OPEN;
import static ru.t1.java.demo.model.enums.TransactionStatus.BLOCKED;
import static ru.t1.java.demo.model.enums.TransactionStatus.REJECTED;
import static ru.t1.java.demo.model.enums.TransactionStatus.REQUESTED;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @LogDataSourceError
    @Override
    public Page<TransactionDtoResponse> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable).
                map(transaction -> modelMapper.map(transaction, TransactionDtoResponse.class));
    }

    @Transactional(readOnly = true)
    @LogDataSourceError
    @Override
    public TransactionDtoResponse getTransaction(Long id) {
        return transactionRepository.findById(id)
                .map(transaction -> modelMapper.map(transaction, TransactionDtoResponse.class))
                .orElseThrow(() -> new EntityNotFoundException(Transaction.class, id));
    }

    @LogDataSourceError
    @Override
    public Long saveTransaction(@Valid TransactionDtoRequest transactionDtoRequest) {
        final Account account = accountService.getAccountById(transactionDtoRequest.accountId());
        final Transaction transaction = modelMapper.map(transactionDtoRequest, Transaction.class);
        transaction.setAccount(account);
        return transactionRepository
                .save(transaction)
                .getId();
    }

    @LogDataSourceError
    @Override
    public TransactionDtoAccept rejectTransactionOperation(@Valid TransactionDtoRequest transactionDtoRequest) {
        final Account account = accountService.getAccountById(transactionDtoRequest.accountId());
        if (!account.getAccountStatus().equals(OPEN)) {
            throw new TransactionException(account.getId());
        }

        final Transaction transaction = modelMapper.map(transactionDtoRequest, Transaction.class);
        transaction.setAccount(account);
        transaction.setTransactionStatus(REQUESTED);
        transaction.setTransactionId(randomUUID());
        transactionRepository.save(transaction);

        final BigDecimal newBalance = account.getBalance().add(transactionDtoRequest.amount());
         account.setBalance(newBalance);

        final TransactionDtoAccept transactionDtoAccept = modelMapper.map(transaction, TransactionDtoAccept.class);
        transactionDtoAccept.setBalance(account.getBalance());
        transactionDtoAccept.setAccountId(transaction.getAccount().getAccountId());
        transactionDtoAccept.setClientId(transaction.getAccount().getClient().getClientId());
        return transactionDtoAccept;
    }

    public void handleResultTransaction(TransactionDtoResult transactionDtoResult) {
        switch (transactionDtoResult.getTransactionStatus()) {
            case ACCEPTED -> {
                final Transaction transaction = transactionRepository.findByTransactionId(transactionDtoResult.getTransactionId());
                transaction.setTransactionStatus(transactionDtoResult.getTransactionStatus());
            }
            case REJECTED -> {
                final Transaction transaction = transactionRepository.findByTransactionId(transactionDtoResult.getTransactionId());
                Account account = accountService.getAccountByAccountId(transactionDtoResult.getAccountId());
                final BigDecimal resetAmount = transactionDtoResult.getAmount();
                account.setBalance(account.getBalance().subtract(resetAmount));
                transaction.setTransactionStatus(REJECTED);
            }
            case BLOCKED -> {
                List<Transaction> transactions =  transactionRepository.findAllByTransactionIdIn(transactionDtoResult.getTransactionsBlocked())
                        .stream()
                        .peek(transaction -> transaction.setTransactionStatus(BLOCKED))
                        .toList();
                transactionRepository.saveAll(transactions);
                BigDecimal frozenAmount = BigDecimal.ZERO;
                for (Transaction transaction : transactions) {
                    frozenAmount = frozenAmount.add(transaction.getAmount());
                }
                Account account = accountService.getAccountByAccountId(transactionDtoResult.getAccountId());
                account.setFrozenAmount(frozenAmount);
                account.setAccountStatus(AccountStatus.BLOCKED);
            }
            default -> throw new UnsupportedOperationException();
        }
    }

}
