--liquibase formatted sql

--changeset anton_narizhny:1
CREATE TABLE IF NOT EXISTS department
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
);

--changeset anton_narizhny:2
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(64) UNIQUE ,
    firstname VARCHAR(64) NOT NULL ,
    lastname VARCHAR(64) NOT NULL ,
    email VARCHAR(32) NOT NULL UNIQUE ,
    phone_number VARCHAR(32) NOT NULL UNIQUE ,
    registration_date DATE ,
    birth_date DATE ,
    salary INT ,
    role VARCHAR(16),
    department_id INT REFERENCES department (id)
);