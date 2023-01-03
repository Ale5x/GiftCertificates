
INSERT INTO roles(id_roles, name) VALUES (1, 'USER'), (2, 'ADMIN');

INSERT INTO statuses(id_status, name) VALUES (1, 'ACTIVE'), (2, 'BLOCKED');

INSERT INTO users(first_name, last_name, email, password, id_status, create_date, update_date)
VALUES ('Carey', 'Mahoney', 'carey_mahoney@gmail.com', '123', 1, '2022-05-10 12:33:33', '2022-05-10 12:33:33'),
       ('Eric', 'Lassard', 'eric_lassard@gmail.com', '123', 1, '2022-05-10 12:33:33', '2022-05-10 12:33:33'),
       ('Eugene', 'Tackleberry', 'eugene_tackleberry@gmail.com', '123', 1, '2022-05-10 12:33:33', '2022-05-10 12:33:33'),
       ('Moses', 'Hightower', 'moses_hightower@gmail.com', '123', 1, '2022-05-10 12:33:33', '2022-05-10 12:33:33');



INSERT INTO users_roles(id_roles, id_users)
VALUES (1, 1), (1, 2), (1, 3), (1, 4);


INSERT INTO orders(cost, purchase_time, id_users)
VALUES (15, '2022-05-10 12:33:33', 1),
       (7, '2022-05-10 12:33:33', 2),
       (55, '2022-05-10 12:33:33', 3);


INSERT INTO tags(name)
VALUES ('Tag 1'), ('Tag 2'), ('Tag 3'), ('Tag 4'), ('Tag 5');


INSERT INTO gift_certificates(name, description, price, duration, create_date, last_update_date)
VALUES ('Auto','Description of the car certificate', 33, 3, '2022-05-10 12:33:33', '2022-05-10 12:33:33'),
       ('Tesla','Description of the Tesla certificate', 100, 50, '2020-01-01 10:10:10', '2022-10-10 10:10:10'),
       ('Object','Description of the object certificate', 300, 150, '2020-05-05 10:10:10', '2022-10-10 10:10:10');


INSERT INTO order_details(price, id_orders, id_certificates)
VALUES (33, 1, 1), (50, 2, 2), (70, 3, 3);


INSERT INTO tags_gift_certificates(id_certificates, id_tags)
VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
       (2, 1), (2, 2), (2, 5),
       (3, 2), (3, 4), (3, 5);

