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
import ru.t1.java.demo.dto.response.TransactionDtoResponse;
import ru.t1.java.demo.exception.EntityNotFoundException;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.AccountService;
import ru.t1.java.demo.service.TransactionService;


@Service
@RequiredArgsConstructor
@Transactional
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
}
