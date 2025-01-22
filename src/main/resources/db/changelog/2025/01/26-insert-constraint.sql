--liquibase formatted sql

--changeset Dmitry Smirnov:12_0
--comment: внесение изменений в таблицу банковских счетов


ALTER TABLE accounts
ALTER COLUMN account_status SET NOT NULL,
ALTER COLUMN account_id SET NOT NULL,
ALTER COLUMN frozen_amount SET NOT NULL,
ADD CONSTRAINT unique_account_id UNIQUE (account_id);

ALTER TABLE client
ALTER COLUMN client_id SET NOT NULL,
ADD CONSTRAINT unique_client_id UNIQUE (client_id);

ALTER TABLE transactions
ALTER COLUMN transaction_status SET NOT NULL,
ALTER COLUMN transaction_id SET NOT NULL,
ADD CONSTRAINT unique_transaction_id UNIQUE (transaction_id);