--liquibase formatted sql

--changeset Dmitry Smirnov:9_0
--comment: внесение изменений в таблицу банковских счетов

ALTER TABLE accounts
ADD COLUMN account_status VARCHAR(30),
ADD COLUMN account_id UUID,
ADD COLUMN frozen_amount DECIMAL(19, 2);
