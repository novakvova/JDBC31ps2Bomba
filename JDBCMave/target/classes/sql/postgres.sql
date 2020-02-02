psql -U postgres

CREATE USER ivan with PASSWORD 'Qwerty1-';

\du

CREATE DATABASE oksanadb;

\l

GRANT ALL PRIVILEGES ON DATABASE "oksanadb" to ivan;

\q


CREATE TABLE tbl_employees
(
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL,
    SALARY decimal(15, 2) NOT NULL,
    CREATED_DATE timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

---DROP TABLE IF EXISTS books, authors, testing, images;

CREATE TABLE IF NOT EXISTS authors (
    id serial PRIMARY KEY, 
    name VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS books (
    id serial PRIMARY KEY, 
    author_id INT references authors(id), title VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS testing(id INT);
CREATE TABLE IF NOT EXISTS images(id serial, data bytea);