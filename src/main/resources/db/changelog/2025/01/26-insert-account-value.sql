--liquibase formatted sql

--changeset Dmitry Smirnov:10_0
--comment: внесение дополнительных данных о счете

CREATE EXTENSION IF NOT EXISTS pgcrypto;

UPDATE accounts
SET frozen_amount = 0,
    account_id = gen_random_uuid();

UPDATE accounts
SET account_status = 'ARRESTED'
WHERE id BETWEEN 1 AND 5;

UPDATE accounts
SET account_status = 'BLOCKED'
WHERE id BETWEEN 6 AND 10;

UPDATE accounts
SET account_status = 'CLOSED'
WHERE id BETWEEN 11 AND 13;

UPDATE accounts
SET account_status = 'OPEN'
WHERE id > 13;