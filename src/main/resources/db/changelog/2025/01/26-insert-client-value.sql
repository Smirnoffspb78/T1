--liquibase formatted sql

--changeset Dmitry Smirnov:10_0
--comment: внесение дополнительных данных о клиенте

CREATE EXTENSION IF NOT EXISTS pgcrypto;

UPDATE client
SET client_id = gen_random_uuid();