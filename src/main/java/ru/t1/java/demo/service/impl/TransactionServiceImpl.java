package ru.t1.java.demo.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.model.enums.AccountStatus;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.AccountService;
import ru.t1.java.demo.service.TransactionService;
import ru.t1.java.demo.service.handler.TransactionStatusHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static ru.t1.java.demo.model.enums.AccountStatus.OPEN;
import static ru.t1.java.demo.model.enums.TransactionStatus.BLOCKED;
import static ru.t1.java.demo.model.enums.TransactionStatus.REJECTED;
import static ru.t1.java.demo.model.enums.TransactionStatus.REQUESTED;


@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    private final ModelMapper modelMapper;

    private final List<TransactionStatusHandler> transactionStatusHandlers;

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
    public Optional<TransactionDtoAccept> rejectTransactionOperation(TransactionDtoRequest transactionDtoRequest) {
        final Account account = accountService.getAccountById(transactionDtoRequest.accountId());
        Optional<TransactionDtoAccept> optionalDto = Optional.empty();
        if (account.getAccountStatus() == OPEN) {
            final Transaction transaction = modelMapper.map(transactionDtoRequest, Transaction.class);
            transaction.setAccount(account);
            transaction.setTransactionStatus(REQUESTED);
            transaction.setTransactionId(randomUUID());
            transactionRepository.save(transaction);

            final BigDecimal newBalance = account.getBalance().add(transactionDtoRequest.amount());
            account.setBalance(newBalance);

            final TransactionDtoAccept transactionDtoAccept = modelMapper.map(transaction, TransactionDtoAccept.class);
            transactionDtoAccept.setBalance(account.getBalance());
            transactionDtoAccept.setAccountId(account.getAccountId());
            transactionDtoAccept.setClientId(account.getClient().getClientId());
            optionalDto = Optional.of(transactionDtoAccept);
        }
        return optionalDto;
    }

    public void handleResultTransaction(TransactionDtoResult transactionDtoResult) {
        transactionStatusHandlers.stream()
                .filter(transactionStatusHandler -> transactionStatusHandler.getStatus() == transactionDtoResult.transactionStatus())
                .findFirst()
                .ifPresent(transactionHandler -> transactionHandler.handleStatus(transactionDtoResult));
    }

    public void acceptTransaction(TransactionDtoResult transactionDtoResult) {
        final Transaction transaction = transactionRepository.findByTransactionId(transactionDtoResult.transactionId());
        transaction.setTransactionStatus(transactionDtoResult.transactionStatus());
    }

    public void rejectedTransaction(TransactionDtoResult transactionDtoResult) {
        final Transaction transaction = transactionRepository.findByTransactionId(transactionDtoResult.transactionId());
        final Account account = accountService.getAccountByAccountId(transactionDtoResult.accountId());
        final BigDecimal resetAmount = transactionDtoResult.amount();
        account.setBalance(account.getBalance().subtract(resetAmount));
        transaction.setTransactionStatus(REJECTED);
    }

    public void blockedTransaction(TransactionDtoResult transactionDtoResult) {
        final List<Transaction> transactions = transactionRepository.findAllByTransactionIdIn(transactionDtoResult.transactionsBlocked());
        BigDecimal frozenAmount = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            frozenAmount = frozenAmount.add(transaction.getAmount());
            transaction.setTransactionStatus(BLOCKED);
        }
        transactionRepository.saveAll(transactions);
        final Account account = accountService.getAccountByAccountId(transactionDtoResult.accountId());
        account.setFrozenAmount(frozenAmount);
        account.setAccountStatus(AccountStatus.BLOCKED);
    }

}
