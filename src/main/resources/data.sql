INSERT INTO users (account_name, email, password, first_name, last_name, type) VALUES ('admin', 'admin@admin.admin', 'admin', 'admin', 'admin', 'ADMIN');
INSERT INTO users (account_name, email, password, first_name, last_name, type) VALUES ('Laci', 'klaszlo@admin.admin', 'admin', 'László', 'Kontra', 'ADMIN');
INSERT INTO users (account_name, email, password, first_name, last_name, type) VALUES ('Feri', 'hferenc@admin.admin', 'admin', 'Ferenc', 'Hajnal', 'ADMIN');

INSERT INTO roles (user_id, role) VALUES ('1', 'ROLE_REGULAR');
INSERT INTO roles (user_id, role) VALUES ('2', 'ROLE_ADMIN');
INSERT INTO roles (user_id, role) VALUES ('3', 'ROLE_ADMIN');
