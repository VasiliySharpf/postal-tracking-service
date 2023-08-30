INSERT INTO tracking.addresses (id, description)
VALUES (1, 'Москва, ул. Рабочая, 20'),
       (2, 'Москва, ул. Весенняя, 1'),
       (3, 'Москва, ул. Ленина, 31'),
       (4, 'Москва, ул. Кирова, 2'),
       (5, 'Москва, ул. Шмидта, 33'),
       (6, 'Москва, Площадь Ильича, 5');

SELECT SETVAL('tracking.addresses_id_seq', (SELECT MAX(id) FROM tracking.addresses));

INSERT INTO tracking.postal_items (id, type, recipient, index_of_recipient, address_of_recipient)
VALUES (1, 'LETTER', 'Иванов Иван Иванович', '1001001', 2),
       (2, 'PACKAGE', 'Петров Петр Петрович', '2002002', 4),
       (3, 'PARCEL', 'Кардашьян Зинаида Михайловна', '3003003', 3),
       (4, 'POSTCARD', 'Маск Илон Артемович', '3003003', 6),
       (5, 'POSTCARD', 'Болконский Андрей Александрович', '1001001', 1);

SELECT SETVAL('tracking.postal_items_id_seq', (SELECT MAX(id) FROM tracking.postal_items));

INSERT INTO tracking.post_offices (index, name, address)
VALUES ('1001001', 'Почтовое отделение 1001001', 1),
       ('2002002', 'Почтовое отделение 2002002', 2),
       ('3003003', 'Почтовое отделение 3003003', 6);

INSERT INTO tracking.postage_statuses (postal_item_id, status)
VALUES (1, 'REGISTERED'),
       (2, 'IN_POST_OFFICE'),
       (3, 'ON_WAY'),
       (4, 'RECEIVED_BY_ADDRESSEE'),
       (5, 'IN_POST_OFFICE');

INSERT INTO tracking.postage_events (id, postal_item_id, post_office_id, event_date, event)
VALUES (1, 2, '1001001', '2023-08-20 08:50:46.890785 +00:00', 'ARRIVAL'),
       (2, 3, '3003003', '2023-08-20 08:50:46.799785 +00:00', 'ARRIVAL'),
       (3, 3, '3003003', '2023-08-20 12:20:47.599763 +00:00', 'DEPARTURE'),
       (4, 4, '2002002', '2023-08-21 09:30:10.789068 +00:00', 'ARRIVAL'),
       (5, 5, '2002002', '2023-08-21 10:00:10.789068 +00:00', 'ARRIVAL'),
       (6, 4, '2002002', '2023-08-21 10:20:47.899777 +00:00', 'DEPARTURE'),
       (7, 4, '3003003', '2023-08-22 09:30:10.569069 +00:00', 'ARRIVAL'),
       (8, 5, '2002002', '2023-08-22 17:50:46.897659 +00:00', 'DEPARTURE'),
       (9, 4, '3003003', '2023-08-23 11:30:11.564388 +00:00', 'DEPARTURE'),
       (10, 5, '1001001', '2023-08-23 20:35:44.675439 +00:00', 'ARRIVAL');

SELECT SETVAL('tracking.postage_events_id_seq', (SELECT MAX(id) FROM tracking.postage_events));