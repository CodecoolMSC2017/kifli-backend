INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('admin', 'admin@admin.admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'admin', 'admin'); --1
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Laci', 'klaszlo@admin.admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'László', 'Kontra'); --2
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Feri', 'hferenc@admin.admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'Ferenc', 'Hajnal'); --3
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Norbi', 'dnorbi@admin.admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'Kristóf', 'Prokaj'); --4
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Isti', 'gyistvan@admin.admin', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'István', 'Győrfi'); --5
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Anna', 'kanna@user.user', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'Anna', 'Kiss'); --6
INSERT INTO users (username, email, password, enabled, first_name, last_name) VALUES ('Béla', 'nbela@user.user', '$2a$10$2Gi3G9XaKalERoIa74OYruEHZyqSUqn10uSiOzk4PvOgL49vejna.', 'true', 'Béla', 'Nagy'); --7

INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Laci', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Feri', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Norbi', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Isti', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Anna', 'ROLE_REGULAR');
INSERT INTO authorities (username, authority) VALUES ('Béla', 'ROLE_REGULAR');

INSERT INTO credentials (user_id, phone, country, state, city, street) VALUES (1, '70 007 0007', 'Hungary', 'Borsod Abaúj Zemplén', 'Szalonna', 'Fa utca 2');
INSERT INTO credentials (user_id, phone, country, state, city, street) VALUES (2, '70 007 0008', 'Hungary', 'Borsod Abaúj Zemplén', 'Miskolc', 'Hajós Alfréd utca 4');
INSERT INTO credentials (user_id, phone, country, state, city, street) VALUES (3, '70 007 0001', 'Hungary', 'Heves', 'Eger', 'Dobó István utca 4');
INSERT INTO credentials (user_id, phone, country, state, city, street) VALUES (4, '70 007 0010', 'Hungary', 'A', 'B', 'C utca x+y^2');
INSERT INTO credentials (user_id, phone, country, state, city, street) VALUES (5, '70 007 0011', 'Hungary', 'Heves', 'Degec', 'Teszopol utca 69.');
INSERT INTO credentials (user_id, phone, country, state, city, street) VALUES (6, '20 358 1974', 'Hungary', 'Bács-Kiskun', 'Kiskunhalas', 'Fogas utca 2');
INSERT INTO credentials (user_id, phone, country, state, city, street) VALUES (7, '70 585 3547', 'Hungary', 'Heves', 'Pétervására', 'Kanalas út 4');

INSERT INTO categories (name) VALUES ('Machine'); -- 1
INSERT INTO categories (name) VALUES ('Farm'); -- 2
INSERT INTO categories (name) VALUES ('Animal'); -- 3
INSERT INTO categories (name) VALUES ('Tool'); -- 4
INSERT INTO categories (name) VALUES ('Plant'); -- 5
INSERT INTO categories (name) VALUES ('Produce'); --6

INSERT INTO category_attributes (category_id, name, type) VALUES (1, 'Color', 'STRING'); -- 1
INSERT INTO category_attributes (category_id, name, type) VALUES (1, 'Performance', 'NUMBER'); -- 2
INSERT INTO category_attributes (category_id, name, type) VALUES (1, 'Functional', 'BOOL'); -- 3
INSERT INTO category_attributes (category_id, name, type) VALUES (2, 'Size', 'NUMBER'); -- 4
INSERT INTO category_attributes (category_id, name, type) VALUES (2, 'Color', 'STRING'); -- 5
INSERT INTO category_attributes (category_id, name, type) VALUES (2, 'Location', 'STRING'); -- 6
INSERT INTO category_attributes (category_id, name, type) VALUES (3, 'Species', 'STRING'); --7
INSERT INTO category_attributes (category_id, name, type) VALUES (3, 'Age', 'NUMBER'); --8
INSERT INTO category_attributes (category_id, name, type) VALUES (3, 'Gender', 'STRING'); --9
INSERT INTO category_attributes (category_id, name, type) VALUES (3, 'Weight', 'NUMBER'); --10
INSERT INTO category_attributes (category_id, name, type) VALUES (4, 'Material', 'STRING'); --11
INSERT INTO category_attributes (category_id, name, type) VALUES (4, 'Used', 'BOOL'); --12
INSERT INTO category_attributes (category_id, name, type) VALUES (5, 'Type', 'STRING'); --13
INSERT INTO category_attributes (category_id, name, type) VALUES (5, 'Ideal Region', 'STRING'); --14
INSERT INTO category_attributes (category_id, name, type) VALUES (5, 'Advantages', 'STRING'); --15
INSERT INTO category_attributes (category_id, name, type) VALUES (5, 'Soil type', 'STRING'); --16
INSERT INTO category_attributes (category_id, name, type) VALUES (6, 'Type', 'STRING'); --17
INSERT INTO category_attributes (category_id, name, type) VALUES (6, 'Quantity/Package', 'STRING'); --18


-- 1
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (3, 1, 'New tractor for sale', 'Front hydraulics, front PTO, susepension front axle, good tires.', 24691.00, 'BUYOUT', 'FOR SALE', to_date('20170725','YYYYMMDD'));
-- 2
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (2, 3, '100 chicken for sale', 'Domestic farmed chickens for sale. The price per piece.', 12.00, 'BUYOUT', 'FOR SALE', to_date('20180618','YYYYMMDD'));
-- 3
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (5, 3, 'Beef for sale', 'Fresh beef for sale! For professional butcher. The price is per kg.', 10.00, 'BUYOUT', 'FOR SALE', to_date('20180619','YYYYMMDD'));
-- 4
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (6, 2, 'Farm for sale', '3000m2, This farm also has the potential to be converted into an organic farm operation.  With deep fertile soils, local access to manure and compost, good irrigation and good rainfall this farm is highly suited for organics.', 300000.00, 'BUYOUT', 'FOR SALE', to_date('20180620','YYYYMMDD'));
-- 5
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (1, 1, 'NX2000 Harvester', 'An awesome piece of machinery, this bad boy can handle any work you throw at it.', 26599.99, 'BUYOUT', 'FOR SALE', to_date('20180621','YYYYMMDD'));
-- 6
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (3, 4, 'Rake for sale, in perfect condition', 'The title says it all.', 8.00, 'BUYOUT', 'FOR SALE', to_date('20180622','YYYYMMDD'));
-- 7
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (6, 4, 'Red wheelbarrow', '8 years old wheelbarrow, with only a few scratches on the side.', 30.00, 'BUYOUT', 'FOR SALE', to_date('20180623','YYYYMMDD'));
-- 8
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (7, 4, '500L tank', '*slaps top of tank* This bad boy can fit so much water in it!', 200.00, 'BUYOUT', 'FOR SALE', to_date('20180623','YYYYMMDD'));
-- 9
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (1, 4, 'High pressure pump', '300W consumption, can SUCC water from 15 meter deep wells.', 170.00, 'BUYOUT', 'FOR SALE', to_date('20180623','YYYYMMDD'));
-- 10
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (1, 3, 'German shephard puppies', '6 cute little puppies are waiting for their new owners!', 15.00, 'BUYOUT', 'FOR SALE', to_date('20180623','YYYYMMDD'));
-- 11
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (2, 4, 'Garden toolset', 'Slightly rusty toolset with everything you need at home to keep your garden tidy.', 40.00, 'BUYOUT', 'FOR SALE', to_date('20180623','YYYYMMDD'));
-- 12
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (1, 5, 'Strawberry plants', 'They just grew onto me, gotta get rid of them.', 3.00, 'BUYOUT', 'FOR SALE', to_date('20180627','YYYYMMDD'));
-- 13
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (4, 1, 'Trailer for harvesting', 'A regular trailer in great shape.', 380.00, 'BUYOUT', 'FOR SALE', to_date('20180627','YYYYMMDD'));
-- 14
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (6, 4, 'Weed killer', 'Not safe to use near children.', 20.00, 'BUYOUT', 'FOR SALE', to_date('20180627','YYYYMMDD'));
-- 15
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (5, 5, 'Hayseed', 'Genetically modified hayseed to grow the food your animals need.', 23.00, 'BUYOUT', 'FOR SALE', to_date('20180629','YYYYMMDD'));
-- 16
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (3, 4, 'Wrenches in multiple sizes', 'Nothing to see here, go buy them!', 6.00, 'BUYOUT', 'FOR SALE', to_date('20180701','YYYYMMDD'));
-- 17
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (2, 4, '30m long hose', 'Unused hose, 3 cm in diameter.', 10.00, 'BUYOUT', 'FOR SALE', to_date('20180701','YYYYMMDD'));
-- 18
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (7, 5, 'Synthetic fertilizer', 'description', 32.50, 'BUYOUT', 'FOR SALE', to_date('20180702','YYYYMMDD'));
-- 19
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (5, 1, 'Sprayer for tractor', 'description', 460.00, 'BUYOUT', 'FOR SALE', to_date('20180703','YYYYMMDD'));
-- 20
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (3, 1, 'Tractor for sale', 'description', 19999.99, 'BUYOUT', 'FOR SALE', to_date('20180703','YYYYMMDD'));
-- 21
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (1, 4, 'Solar panel', 'description', 230.00, 'BUYOUT', 'FOR SALE', to_date('20180703','YYYYMMDD'));
-- 22
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (3, 1, 'Grain crusher', 'description', 90.00, 'BUYOUT', 'FOR SALE', to_date('20180703','YYYYMMDD'));
-- 23
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (4, 1, 'Tractor inner tires', 'description', 9.99, 'BUYOUT', 'FOR SALE', to_date('20180714','YYYYMMDD'));
-- 24
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (5, 4, 'Cow drinkers', 'description', 34.00, 'BUYOUT', 'FOR SALE', to_date('20180715','YYYYMMDD'));
-- 25
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (7, 5, 'Sunflower seeds', 'description', 2.20, 'BUYOUT', 'FOR SALE', to_date('20180716','YYYYMMDD'));
-- 26
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (2, 5, 'Oak saplings', 'description', 64.50, 'BUYOUT', 'FOR SALE', to_date('20180719','YYYYMMDD'));
-- 27
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (4, 5, 'Hay bale', 'description', 98.50, 'BUYOUT', 'FOR SALE', to_date('20180719','YYYYMMDD'));
-- 28
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (5, 1, 'BOJLER ELADÓ', 'NEM LOPOTT, alig használt, alkuképes', 150.00, 'BUYOUT', 'FOR SALE', to_date('20180720','YYYYMMDD'));
-- 29
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (4, 5, 'Organic fertilizer', 'description', 24.00, 'BUYOUT', 'FOR SALE', to_date('20180720','YYYYMMDD'));
-- 30
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (6, 5, 'Corn for sale', 'description', 13.00, 'BUYOUT', 'FOR SALE', to_date('20180720','YYYYMMDD'));
-- 31
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (5, 1, 'Water tower', 'description', 1973.70, 'BUYOUT', 'FOR SALE', to_date('20180723','YYYYMMDD'));
-- 32
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (7, 2, 'Farming plot', 'description', 18000.00, 'BUYOUT', 'FOR SALE', to_date('20180724','YYYYMMDD'));
-- 33
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (5, 1, 'Yet another tractor for sale', 'description', 26450.00, 'BUYOUT', 'FOR SALE', to_date('20180724','YYYYMMDD'));
-- 34
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (1, 3, 'Beehive', 'description', 386.00, 'BUYOUT', 'FOR SALE', to_date('20180724','YYYYMMDD'));
-- 35
INSERT INTO product_ads (user_id, category_id, title, description, price, type, state, upload_date) VALUES (3, 1, 'Horse-draw carrige', 'description', 132.90, 'BUYOUT', 'FOR SALE', to_date('20180725','YYYYMMDD'));


INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (1, 1, 'green');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (1, 2, '2');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (1, 3, 'true');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (2, 7, 'Ancona');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (2, 8, '1');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (2, 9, 'Female');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (2, 10, '6');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (3, 17, 'Meat');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (3, 18, '1kg/pkg');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (4, 4, '3');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (4, 5, 'brown');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (4, 6, 'right here bro');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (5, 1, 'Green');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (5, 2, '10');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (5, 3, 'true');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (6, 11, 'steel');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (6, 12, 'false');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (7, 11, 'Wood');
INSERT INTO product_attributes (product_id, attribute_id, value) VALUES (7, 12, 'true');

INSERT INTO product_pictures (product_id) VALUES (1); -- 1
INSERT INTO product_pictures (product_id) VALUES (1); -- 2
INSERT INTO product_pictures (product_id) VALUES (2); -- 3
INSERT INTO product_pictures (product_id) VALUES (2); -- 4
INSERT INTO product_pictures (product_id) VALUES (2); -- 5
INSERT INTO product_pictures (product_id) VALUES (2); -- 6
INSERT INTO product_pictures (product_id) VALUES (3); -- 7
INSERT INTO product_pictures (product_id) VALUES (3); -- 8
INSERT INTO product_pictures (product_id) VALUES (3); -- 9
INSERT INTO product_pictures (product_id) VALUES (4); -- 10
INSERT INTO product_pictures (product_id) VALUES (5); -- 11
INSERT INTO product_pictures (product_id) VALUES (6); -- 12
INSERT INTO product_pictures (product_id) VALUES (6); -- 13
INSERT INTO product_pictures (product_id) VALUES (6); -- 14
INSERT INTO product_pictures (product_id) VALUES (6); -- 15
INSERT INTO product_pictures (product_id) VALUES (6); -- 16
INSERT INTO product_pictures (product_id) VALUES (7); -- 17
INSERT INTO product_pictures (product_id) VALUES (7); -- 18
INSERT INTO product_pictures (product_id) VALUES (7); -- 19
INSERT INTO product_pictures (product_id) VALUES (7); -- 20
INSERT INTO product_pictures (product_id) VALUES (8); -- 21
INSERT INTO product_pictures (product_id) VALUES (8); -- 22
INSERT INTO product_pictures (product_id) VALUES (11); -- 23
INSERT INTO product_pictures (product_id) VALUES (11); -- 24
INSERT INTO product_pictures (product_id) VALUES (11); -- 25
INSERT INTO product_pictures (product_id) VALUES (12); -- 26
INSERT INTO product_pictures (product_id) VALUES (12); -- 27
INSERT INTO product_pictures (product_id) VALUES (12); -- 28
INSERT INTO product_pictures (product_id) VALUES (13); -- 29
INSERT INTO product_pictures (product_id) VALUES (13); -- 30
INSERT INTO product_pictures (product_id) VALUES (15); -- 31
INSERT INTO product_pictures (product_id) VALUES (15); -- 32
INSERT INTO product_pictures (product_id) VALUES (15); -- 33
INSERT INTO product_pictures (product_id) VALUES (16); -- 34
INSERT INTO product_pictures (product_id) VALUES (16); -- 35
INSERT INTO product_pictures (product_id) VALUES (17); -- 36
INSERT INTO product_pictures (product_id) VALUES (17); -- 37
INSERT INTO product_pictures (product_id) VALUES (18); -- 38
INSERT INTO product_pictures (product_id) VALUES (18); -- 39
INSERT INTO product_pictures (product_id) VALUES (20); -- 40
INSERT INTO product_pictures (product_id) VALUES (20); -- 41
INSERT INTO product_pictures (product_id) VALUES (21); -- 42
INSERT INTO product_pictures (product_id) VALUES (21); -- 43
INSERT INTO product_pictures (product_id) VALUES (22); -- 44
INSERT INTO product_pictures (product_id) VALUES (22); -- 45
INSERT INTO product_pictures (product_id) VALUES (22); -- 46
INSERT INTO product_pictures (product_id) VALUES (22); -- 47
INSERT INTO product_pictures (product_id) VALUES (22); -- 48
INSERT INTO product_pictures (product_id) VALUES (22); -- 49
INSERT INTO product_pictures (product_id) VALUES (22); -- 50
INSERT INTO product_pictures (product_id) VALUES (23); -- 51
INSERT INTO product_pictures (product_id) VALUES (23); -- 52
INSERT INTO product_pictures (product_id) VALUES (23); -- 53
INSERT INTO product_pictures (product_id) VALUES (23); -- 54
INSERT INTO product_pictures (product_id) VALUES (24); -- 55
INSERT INTO product_pictures (product_id) VALUES (24); -- 56
INSERT INTO product_pictures (product_id) VALUES (24); -- 57
INSERT INTO product_pictures (product_id) VALUES (24); -- 58
INSERT INTO product_pictures (product_id) VALUES (24); -- 59
INSERT INTO product_pictures (product_id) VALUES (25); -- 60
INSERT INTO product_pictures (product_id) VALUES (25); -- 61
INSERT INTO product_pictures (product_id) VALUES (25); -- 62
INSERT INTO product_pictures (product_id) VALUES (25); -- 63
INSERT INTO product_pictures (product_id) VALUES (25); -- 64
INSERT INTO product_pictures (product_id) VALUES (25); -- 65
INSERT INTO product_pictures (product_id) VALUES (25); -- 66
INSERT INTO product_pictures (product_id) VALUES (25); -- 67
INSERT INTO product_pictures (product_id) VALUES (26); -- 68
INSERT INTO product_pictures (product_id) VALUES (26); -- 69
INSERT INTO product_pictures (product_id) VALUES (26); -- 70
INSERT INTO product_pictures (product_id) VALUES (26); -- 71
INSERT INTO product_pictures (product_id) VALUES (27); -- 72
INSERT INTO product_pictures (product_id) VALUES (27); -- 73
INSERT INTO product_pictures (product_id) VALUES (28); -- 74
INSERT INTO product_pictures (product_id) VALUES (28); -- 75
INSERT INTO product_pictures (product_id) VALUES (28); -- 76
INSERT INTO product_pictures (product_id) VALUES (29); -- 77
INSERT INTO product_pictures (product_id) VALUES (29); -- 78
INSERT INTO product_pictures (product_id) VALUES (29); -- 79
INSERT INTO product_pictures (product_id) VALUES (30); -- 80
INSERT INTO product_pictures (product_id) VALUES (30); -- 81
INSERT INTO product_pictures (product_id) VALUES (30); -- 82
INSERT INTO product_pictures (product_id) VALUES (30); -- 83
INSERT INTO product_pictures (product_id) VALUES (31); -- 84
INSERT INTO product_pictures (product_id) VALUES (31); -- 85
INSERT INTO product_pictures (product_id) VALUES (32); -- 86
INSERT INTO product_pictures (product_id) VALUES (32); -- 87
INSERT INTO product_pictures (product_id) VALUES (32); -- 88
INSERT INTO product_pictures (product_id) VALUES (32); -- 89
INSERT INTO product_pictures (product_id) VALUES (33); -- 90
INSERT INTO product_pictures (product_id) VALUES (33); -- 91
INSERT INTO product_pictures (product_id) VALUES (33); -- 92
INSERT INTO product_pictures (product_id) VALUES (33); -- 93
INSERT INTO product_pictures (product_id) VALUES (34); -- 94
INSERT INTO product_pictures (product_id) VALUES (34); -- 95
INSERT INTO product_pictures (product_id) VALUES (34); -- 96
INSERT INTO product_pictures (product_id) VALUES (34); -- 97
INSERT INTO product_pictures (product_id) VALUES (35); -- 98
INSERT INTO product_pictures (product_id) VALUES (35); -- 99
INSERT INTO product_pictures (product_id) VALUES (35); -- 100
