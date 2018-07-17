INSERT INTO users (account_name, email, password, first_name, last_name, type) VALUES ('admin', 'admin@admin.admin', 'admin', 'admin', 'admin', 'ADMIN'); --1
INSERT INTO users (account_name, email, password, first_name, last_name, type) VALUES ('Laci', 'klaszlo@admin.admin', 'admin', 'László', 'Kontra', 'ADMIN'); --2
INSERT INTO users (account_name, email, password, first_name, last_name, type) VALUES ('Feri', 'hferenc@admin.admin', 'admin', 'Ferenc', 'Hajnal', 'ADMIN'); --3
INSERT INTO users (account_name, email, password, first_name, last_name, type) VALUES ('Kristóf', 'pkristof@admin.admin', 'admin', 'Kristóf', 'Prokaj', 'ADMIN'); --4
INSERT INTO users (account_name, email, password, first_name, last_name, type) VALUES ('Isti', 'gyistvan@admin.admin', 'admin', 'István', 'Győrfi', 'ADMIN'); --5
INSERT INTO users (account_name, email, password, first_name, last_name, type) VALUES ('Anna', 'kanna@user.user', 'user', 'Anna', 'Kiss', 'USER'); --6
INSERT INTO users (account_name, email, password, first_name, last_name, type) VALUES ('Béla', 'nbela@user.user', 'user', 'Béla', 'Nagy', 'USER'); --7

INSERT INTO roles (user_id, role) VALUES ('1', 'ROLE_REGULAR');
INSERT INTO roles (user_id, role) VALUES ('2', 'ROLE_ADMIN');
INSERT INTO roles (user_id, role) VALUES ('3', 'ROLE_ADMIN');
INSERT INTO roles (user_id, role) VALUES ('4', 'ROLE_ADMIN');
INSERT INTO roles (user_id, role) VALUES ('5', 'ROLE_ADMIN');
INSERT INTO roles (user_id, role) VALUES ('6', 'ROLE_USER');
INSERT INTO roles (user_id, role) VALUES ('7', 'ROLE_USER');

INSERT INTO categories (name) VALUES ('MACHINE'); --1
INSERT INTO categories (name) VALUES ('FARM'); --2
INSERT INTO categories (name) VALUES ('ANIMAL'); --3

INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (3, 1, 'NEW TRACTOR FOR SALE', 'Front hydraulics, Front PTO, Susepension front axle, Good tires', 24691.00, 'BUYOUT', 'FOR SALE', to_date('20150725','YYYYMMDD'));
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (2, 3, '100 chicken FOR SALE', 'Domestic farmed chickens for sale. The price per piece.', 12.00, 'BUYOUT', 'FOR SALE', to_date('20180720','YYYYMMDD'));
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (5, 3, 'BEEF FOR SALE', 'Fresh beef for sale! For professional butcher. The price per kg.', 10.00, 'BUYOUT', 'FOR SALE', to_date('20180718','YYYYMMDD'));
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (6, 2, 'FARM FOR SALE', '3000m2, This farm also has the potential to be converted into an organic farm operation.  With deep fertile soils, local access to manure and compost, good irrigation and good rainfall this farm is highly suited for organics.', 300000.00, 'BUYOUT', 'FOR SALE', to_date('20160425','YYYYMMDD'));
