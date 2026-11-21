--liquibase formatted sql

--changeset sachin:make-email-column-user-not-null
alter table user_entity alter column email set not null;


