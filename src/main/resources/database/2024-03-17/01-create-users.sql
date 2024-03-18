--liquibase formatted sql
--changeset falynsky:1
CREATE TABLE Users
(
    username   VARCHAR(50) PRIMARY KEY,
    password   VARCHAR(255) NOT NULL,
    enabled    BOOLEAN NOT NULL
);