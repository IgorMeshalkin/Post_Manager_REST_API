TRUNCATE TABLE events, postal_items RESTART IDENTITY;

insert into postal_items (track_number, type, recipient_index, recipient_address, recipient_name, created, last_updated)
values ('PK23081613221736', 'PACKAGE', 680001, 'Адрес Иванова', 'Иванов Иван', current_timestamp, current_timestamp),
       ('PP23081613224633', 'PARCEL_POST', 680002, 'Адрес Сергеева', 'Сергеев Сергей', current_timestamp,
        current_timestamp),
       ('PC23081713211822', 'POSTCARD', 680003, 'Адрес Антонова', 'Антонов Антон', current_timestamp,
        current_timestamp);

insert into events (post_office_id, postal_item_id, type, active_status, created, last_updated)
values (null, 1, 'REGISTRATION', null, current_timestamp, current_timestamp),
       (null, 2, 'REGISTRATION', null, current_timestamp, current_timestamp),
       (null, 3, 'REGISTRATION', null, current_timestamp, current_timestamp),
       (3, 3, 'ARRIVED_AT_THE_POST_OFFICE', true, current_timestamp, current_timestamp),
       (3, 2, 'ARRIVED_AT_THE_POST_OFFICE', true, current_timestamp, current_timestamp);

update events set active_status = false where id = 5;

insert into events (post_office_id, postal_item_id, type, active_status, created, last_updated)
values (3, 2, 'LEFT_POST_OFFICE', null, current_timestamp, current_timestamp);

