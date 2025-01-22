--liquibase formatted sql

--changeset Dmitry Smirnov:11_0
--comment: внесение дополнительных данных о транзакциях

CREATE EXTENSION IF NOT EXISTS pgcrypto;

UPDATE transactions
SET transaction_id = gen_random_uuid();

UPDATE transactions
SET transaction_status = 'ACCEPTED'
