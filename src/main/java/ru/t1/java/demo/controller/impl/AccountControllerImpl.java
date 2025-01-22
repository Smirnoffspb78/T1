package ru.t1.java.demo.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.demo.annotation.Metric;
import ru.t1.java.demo.controller.AccountController;
import ru.t1.java.demo.dto.request.AccountDtoRequest;
import ru.t1.java.demo.dto.response.AccountDtoResponse;
import ru.t1.java.demo.service.AccountService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
@Validated
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    @GetMapping("{id}")
    @Metric(validTime = 1)
    public AccountDtoResponse getAccount(@PathVariable(name = "id") Long id) {
        return accountService.getAccount(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    @Metric(validTime = 1)
    public Long createAccount(@Valid @RequestBody AccountDtoRequest accountDtoRequest) {
        return accountService.createAccount(accountDtoRequest);
    }

    @Override
    @GetMapping
    @Metric(validTime = 1)
    public Page<AccountDtoResponse> getAllAccounts(Pageable pageable) {
        return accountService.getAllAccounts(pageable);
    }
}
