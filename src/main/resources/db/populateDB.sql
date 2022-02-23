DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2022-02-23 07:00', 'завтрак', 1200),
       (100000, '2022-02-23 13:00', 'обед', 500),
       (100000, '2022-02-23 18:00', 'ужин', 600),
       (100000, '2022-02-22 08:00', 'завтрак', 600),
       (100000, '2022-02-22 14:00', 'обед', 1000),
       (100000, '2022-02-22 19:00', 'ужин', 700),
       (100001, '2022-02-22 08:00', 'завтрак', 600),
       (100001, '2022-02-22 14:00', 'обед', 1000),
       (100001, '2022-02-22 19:00', 'ужин', 700);