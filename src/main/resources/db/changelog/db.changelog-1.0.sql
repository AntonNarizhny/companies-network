--liquibase formatted sql

--changeset anton_narizhny:1
CREATE TABLE IF NOT EXISTS department
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
);

--changeset anton_narizhny:2
CREATE TABLE IF NOT EXISTS employee
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(64) UNIQUE ,
    password VARCHAR(128) ,
    activation_code UUID ,
    activated BOOLEAN NOT NULL ,
    firstname VARCHAR(64) NOT NULL ,
    lastname VARCHAR(64) NOT NULL ,
    avatar VARCHAR(64) NOT NULL ,
    email VARCHAR(32) NOT NULL UNIQUE ,
    phone_number VARCHAR(32) UNIQUE ,
    birth_date DATE ,
    registration_date DATE ,
    last_visit TIMESTAMP ,
    salary INT ,
    role VARCHAR(16) ,
    department_id INT REFERENCES department (id)
);