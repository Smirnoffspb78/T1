--liquibase formatted sql

--changeset Dmitry Smirnov:8_0
--comment: внесение изменений в таблицу транзакций

ALTER TABLE transactions
ADD COLUMN transaction_status VARCHAR(30),
ADD COLUMN transaction_id UUID;