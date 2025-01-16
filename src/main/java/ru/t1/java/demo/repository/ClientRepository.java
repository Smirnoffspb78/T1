package ru.t1.java.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.demo.annotation.LogDataSourceError;
import ru.t1.java.demo.model.Client;

import java.util.Optional;

@LogDataSourceError
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Override
    Optional<Client> findById(Long aLong);
}