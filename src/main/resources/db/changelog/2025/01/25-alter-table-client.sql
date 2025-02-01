--liquibase formatted sql

--changeset Dmitry Smirnov:7_0
--comment: внесение изменений в таблицу клиентов

ALTER TABLE client
ADD COLUMN client_id UUID;