package ru.t1.java.demo.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.demo.controller.AccountController;
import ru.t1.java.demo.dto.AccountDtoRequest;
import ru.t1.java.demo.dto.AccountDtoResponse;
import ru.t1.java.demo.service.AccountService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AccountDtoResponse> getAccount(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(accountService.getAccount(id), OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<Long> createAccount(@RequestBody AccountDtoRequest accountDtoRequest) {
        return new ResponseEntity<>(accountService.createAccount(accountDtoRequest), CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<AccountDtoResponse>> getAllAccounts(Pageable pageable) {
        return new ResponseEntity<>(accountService.getAllAccounts(pageable), OK);
    }
}
