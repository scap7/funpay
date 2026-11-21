--liquibase formatted sql


--changeset sachin:add-non-null-to-password
alter table user_entity alter column password set not null