--liquibase formatted sql

-- changeset sachin:add-password-column-and-email-to-user
ALTER TABLE user_entity
ADD COLUMN password VARCHAR(255), 
ADD COLUMN email VARCHAR(255);