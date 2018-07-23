INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('admin', 'admin@admin.admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'admin', 'admin'); --1
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Laci', 'klaszlo@admin.admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'László', 'Kontra'); --2
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Feri', 'hferenc@admin.admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'Ferenc', 'Hajnal'); --3
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Kristóf', 'pkristof@admin.admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'Kristóf', 'Prokaj'); --4
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Isti', 'gyistvan@admin.admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'István', 'Győrfi'); --5
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Anna', 'kanna@user.user', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'Anna', 'Kiss'); --6
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Béla', 'nbela@user.user', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'Béla', 'Nagy'); --7

INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Laci', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Feri', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Kristóf', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Isti', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Anna', 'ROLE_REGULAR');
INSERT INTO authorities (username, authority) VALUES ('Béla', 'ROLE_REGULAR');

INSERT INTO credentials (user_id, phone, country, state, city, street) VALUES (6, '20 358 1974', 'Hungary', 'Bács-Kiskun', 'Kiskunhalas', 'Fogas utca 2');
INSERT INTO credentials (user_id, phone, country, state, city, street) VALUES (7, '70 585 3547', 'Hungary', 'Heves', 'Pétervására', 'Kanalas út 4');

INSERT INTO categories (name) VALUES ('MACHINE'); --1
INSERT INTO categories (name) VALUES ('FARM'); --2
INSERT INTO categories (name) VALUES ('ANIMAL'); --3

INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (3, 1, 'NEW TRACTOR FOR SALE', 'Front hydraulics, Front PTO, Susepension front axle, Good tires', 24691.00, 'BUYOUT', 'FOR SALE', to_date('20150725','YYYYMMDD'));
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (2, 3, '100 chicken FOR SALE', 'Domestic farmed chickens for sale. The price per piece.', 12.00, 'BUYOUT', 'FOR SALE', to_date('20180720','YYYYMMDD'));
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (5, 3, 'BEEF FOR SALE', 'Fresh beef for sale! For professional butcher. The price per kg.', 10.00, 'BUYOUT', 'FOR SALE', to_date('20180718','YYYYMMDD'));
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (6, 2, 'FARM FOR SALE', '3000m2, This farm also has the potential to be converted into an organic farm operation.  With deep fertile soils, local access to manure and compost, good irrigation and good rainfall this farm is highly suited for organics.', 300000.00, 'BUYOUT', 'FOR SALE', to_date('20160425','YYYYMMDD'));
