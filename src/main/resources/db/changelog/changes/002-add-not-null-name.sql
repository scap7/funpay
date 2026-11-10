--liquibase formatted sql

--changeset sachin:002-normalize-existing-names
UPDATE user_entity SET name = 'ANONYMOUS' WHERE name IS NULL;

--changeset sachin:002-enforce-not-null-name
ALTER TABLE user_entity ALTER COLUMN name SET NOT NULL;