--liquibase formatted sql

--changeset asoldatov:users0
create sequence if not exists hibernate_sequence
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;
--rollback drop sequence hibernate_sequence;
--comment: Добавлен hibernate_sequence

--changeset asoldatov:users1
create table if not exists users(
    id       int8 default nextval('hibernate_sequence'::regclass) not null primary key unique,
    login    varchar(64)                                          not null unique,
    password varchar(256)                                         not null
);
--rollback drop table users;
--comment: Создана таблица users

--changeset asoldatov:users2
create table if not exists roles
(
    id   int8 default nextval('hibernate_sequence'::regclass) not null primary key unique,
    name varchar(64)                                          not null unique
    );
--rollback drop table roles;
--comment: Создана таблица roles

--changeset asoldatov:users3
create table if not exists user_roles
(
    user_id int8 NOT NULL,
    role_id int8 NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_roles_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_roles_ibfk_2 FOREIGN KEY (role_id) REFERENCES roles (id)
 );
--rollback drop table user_roles;
--comment: Создана таблица user_roles

--changeset asoldatov:users4
insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');
--comment: Вставка ролей по-умолчанию

--changeset asoldatov:genre1
create table if not exists genre
(
    id   int8 default nextval('hibernate_sequence'::regclass) not null primary key unique,
    name varchar(64)                                          not null unique
);
--rollback drop table genre;
--comment: Создана таблица genre

--changeset asoldatov:products1
CREATE TABLE IF NOT EXISTS product
(
    id   int8 default nextval('hibernate_sequence'::regclass) not null primary key unique,
    name varchar(64) NOT NULL,
    amount numeric(10,2) NOT NULL,
    description varchar(255) NOT NULL,
    author varchar(64) NOT NULL,
    genre_id int8 NOT NULL,
    year_of_publication int8 NOT NULL,
    created_at timestamp NOT NULL,
    age_limit int8 NOT NULL,
    is_active bool,
    CONSTRAINT product_genre_ibfk_1 FOREIGN KEY (genre_id) REFERENCES genre (id)
);
--rollback drop table products;
--comment: Создана таблица products

--changeset asoldatov:products2
CREATE TABLE IF NOT EXISTS basket
(
    user_id int8 NOT NULL primary key unique,
    CONSTRAINT basket_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id)
);
--rollback drop table basket;
--comment: Создана таблица basket

--changeset asoldatov:products3
CREATE TABLE IF NOT EXISTS product_item
(
    id   int8 default nextval('hibernate_sequence'::regclass) not null primary key unique,
    count int8 NOT NULL,
    product_id int8 NOT NULL,
    basket_id int8 NOT NULL,
    CONSTRAINT product_item_ibfk_1 FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT product_item_ibfk_2 FOREIGN KEY (basket_id) REFERENCES basket (user_id)
);
--rollback drop table product_item;
--comment: Создана таблица product_item