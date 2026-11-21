--liquibase formatted sql


--changeset sachin:add-unique-constraint-to-email-users
alter table user_entity add constraint uk_user_entity_email unique (email)
