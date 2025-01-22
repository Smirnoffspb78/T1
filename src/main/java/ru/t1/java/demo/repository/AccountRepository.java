package ru.t1.java.demo.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.demo.model.Account;

import java.util.Optional;
import java.util.UUID;


public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = {"client"})
    @Override
    @NonNull
    Page<Account> findAll(@NonNull Pageable pageable);

    Optional<Account> findByAccountId(UUID accountId);
}
