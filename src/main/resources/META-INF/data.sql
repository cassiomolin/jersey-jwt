INSERT INTO user (id, first_name, last_name, email, username, password, active) VALUES (1, 'John', 'Doe', 'admin@example.org', 'admin', '$2a$10$32Hw5zY7uvx0EgsfE/yQNuuL.Nrt5srepNP.vlmES2VKbYAnOBqC6', TRUE);
INSERT INTO user (id, first_name, last_name, email, username, password, active) VALUES (2, 'Jane', 'Doe', 'user@example.org', 'user', '$2a$10$32Hw5zY7uvx0EgsfE/yQNuuL.Nrt5srepNP.vlmES2VKbYAnOBqC6', TRUE);
INSERT INTO user (id, first_name, last_name, email, username, password, active) VALUES (3, 'Johnny', 'Doe', 'disabled@example.org', 'disabled', '$2a$10$32Hw5zY7uvx0EgsfE/yQNuuL.Nrt5srepNP.vlmES2VKbYAnOBqC6',FALSE);

INSERT INTO user_authorities (user_id, authority) VALUES (1, 'ADMIN');
INSERT INTO user_authorities (user_id, authority) VALUES (1, 'USER');
INSERT INTO user_authorities (user_id, authority) VALUES (2, 'USER');
INSERT INTO user_authorities (user_id, authority) VALUES (3, 'USER');