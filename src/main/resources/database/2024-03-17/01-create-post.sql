--liquibase formatted sql
--changeset falynsky:1
CREATE TABLE POST
(
    id      SERIAL PRIMARY KEY,
    title   VARCHAR(255) NOT NULL,
    content VARCHAR(255)
);