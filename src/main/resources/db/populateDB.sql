DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM menu_items;
DELETE FROM lunches;
DELETE FROM users;
DELETE FROM restaurants;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', '{noop}password'),
  ('User2', 'user2@yandex.ru', '{noop}password2'),
  ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_ADMIN', 100002);

INSERT INTO restaurants (name) VALUES
  ('Dominos Pizza'),
  ('Pizza Tempo'),
  ('Super Pizza');

INSERT INTO lunches (name, price) VALUES
  ('Маргарита', 10),
  ('Карбонара', 11),
  ('Барбекю', 12),
  ('Грибная', 13);

INSERT INTO menu_items (registered, restaurant_id, lunch_id) VALUES
  ('2019-06-05', 100003, 100006),
  ('2019-06-05', 100003, 100007),
  (now, 100003, 100008),
  (now, 100003, 100009),
  ('2019-06-05', 100004, 100006),
  ('2019-06-05', 100004, 100007),
  (now, 100004, 100008),
  (now, 100004, 100009);

INSERT INTO votes (registered, restaurant_id, user_id) VALUES
  ('2019-06-05', 100003, 100000),
  (now, 100003, 100000);