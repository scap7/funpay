--liquibase formatted sql

--changeset sachin:001-create-users
CREATE TABLE user_entity (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255),
    gender VARCHAR(20),
    age INT
);
--rollback DROP TABLE user_entity;