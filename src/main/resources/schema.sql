DROP TABLE IF EXISTS product_attributes CASCADE;
DROP TABLE IF EXISTS category_attributes CASCADE;
DROP TABLE IF EXISTS bids CASCADE;
DROP TABLE IF EXISTS product_pictures CASCADE;
DROP TABLE IF EXISTS product_ads CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS credentials CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS verification_number CASCADE;
DROP TYPE IF EXISTS data_type;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(30) UNIQUE,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

CREATE TABLE verification_number (
    user_id INTEGER NOT NULL UNIQUE,
    verification_number INTEGER default null,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE authorities (
    username varchar(50) NOT NULL,
    authority varchar(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username),
    UNIQUE (username, authority)
);

CREATE TABLE credentials (
    user_id INTEGER NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL UNIQUE,
    country VARCHAR(30) NOT NULL,
    state VARCHAR(30) NOT NULL,
    city VARCHAR(30) NOT NULL,
    street VARCHAR(30) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE product_ads (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    category_id INTEGER,
    title VARCHAR(50) NOT NULL,
    description TEXT,
    price REAL NOT NULL,
    type VARCHAR(10) NOT NULL,
    state VARCHAR(10) NOT NULL,
    upload_date DATE NOT NULL,
    premium_expiration_date DATE,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TYPE data_type AS ENUM ('STRING', 'NUMBER', 'BOOL');

CREATE TABLE category_attributes (
    id SERIAL PRIMARY KEY,
    category_id INTEGER,
    name VARCHAR(20) NOT NULL,
    type VARCHAR(15) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    UNIQUE (category_id, name)
);

CREATE TABLE product_attributes (
    id SERIAL PRIMARY KEY,
    product_id INTEGER,
    attribute_id INTEGER,
    value VARCHAR(30) NOT NULL,
    FOREIGN KEY (attribute_id) REFERENCES category_attributes(id),
    FOREIGN KEY(product_id) REFERENCES product_ads(id),
    UNIQUE (product_id, attribute_id)
);

CREATE TABLE product_pictures (
    id SERIAL NOT NULL,
    product_id INTEGER NOT NULL,
    FOREIGN KEY(product_id) REFERENCES product_ads(id)
);

CREATE TABLE bids (
    user_id INTEGER NOT NULL,
    product_id INTEGER,
    amount INTEGER NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY(product_id) REFERENCES product_ads(id)
);