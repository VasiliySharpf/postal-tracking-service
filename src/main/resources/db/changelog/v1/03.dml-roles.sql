--liquibase formatted sql

--changeset vasiliy.sharpf:1
INSERT INTO tracking.roles(name)
VALUES ('ADMIN'), ('USER'), ('POST_WORKER');

--changeset vasiliy.sharpf:2
INSERT INTO tracking.users(username, first_name, last_name, email, password)
VALUES ('admin', '', '', '', '{noop}admin');

--changeset vasiliy.sharpf:3
INSERT INTO tracking.user_roles(user_id, role_id)
VALUES (1, 1)
