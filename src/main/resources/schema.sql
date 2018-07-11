DROP TABLE IF EXISTS product_attributes;
DROP TABLE IF EXISTS category_attributes;
DROP TABLE IF EXISTS bids;
DROP TABLE IF EXISTS product_pictures;
DROP TABLE IF EXISTS premium_ads;
DROP TABLE IF EXISTS product_ads;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS credentials;
DROP TABLE IF EXISTS premium_users;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    account_name VARCHAR(20),
    email VARCHAR(30),
    password VARCHAR(50),
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    type VARCHAR(10)
);

CREATE TABLE premium_users (
    user_id INTEGER,
    expiration_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE credentials (
    user_id INTEGER,
    phone VARCHAR(20),
    country VARCHAR(30),
    state VARCHAR(30),
    city VARCHAR(30),
    street VARCHAR(30),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20)
);

CREATE TABLE product_ads (
    product_id SERIAL PRIMARY KEY,
    user_id INTEGER,
    category_id INTEGER,
    title VARCHAR(50),
    description TEXT,
    price REAL,
    type VARCHAR(10),
    state VARCHAR(10),
    upload_date DATE,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE category_attributes (
    id SERIAL PRIMARY KEY,
    category_id INTEGER,
    name VARCHAR(20),
    type VARCHAR(10),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE product_attributes (
    product_id INTEGER,
    attribute_id INTEGER,
    string_value VARCHAR(30),
    int_value INTEGER,
    bool_value BOOLEAN,
    real_value REAL,
    FOREIGN KEY (attribute_id) REFERENCES category_attributes(id),
    FOREIGN KEY(product_id) REFERENCES product_ads(product_id)
);

CREATE TABLE premium_ads (
    product_id INTEGER,
    expiration_date DATE,
    FOREIGN KEY(product_id) REFERENCES product_ads(product_id)
);

CREATE TABLE product_pictures (
    product_id INTEGER,
    image_url VARCHAR(100),
    FOREIGN KEY(product_id) REFERENCES product_ads(product_id)
);

CREATE TABLE bids (
    user_id INTEGER,
    product_id INTEGER,
    amount INTEGER,
    timestamp TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY(product_id) REFERENCES product_ads(product_id)
);
