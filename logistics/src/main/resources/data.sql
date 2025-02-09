INSERT INTO transport_plan (income) VALUES
    (2000000),
    (5000000);

INSERT INTO address (iso_code, city, street, zip_code, house_number, latitude, longitude) VALUES
    ('HU', 'AA', 'Alpha street', 1100, 11, 45.00, 45.00),
    ('HU', 'BB', 'Beta street', 1100, 22, 48.00, 48.00),
    ('HU', 'CC', 'Gamma street', 2200, 33, 51.00, 51.00),
    ('HU', 'DD', 'Delta street', 2200, 11, 54.00, 54.00),
    ('HU', 'EE', 'Epsilon street', 3300, 22, 57.00, 57.00),
    ('HU', 'FF', 'Franklin street', 3300, 33, 60.00, 60.00),
    ('SK', 'AA', 'Alpha street', 1100, 11, 45.00, 45.00),
    ('SK', 'BB', 'Beta street', 1100, 22, 48.00, 48.00),
    ('SK', 'CC', 'Gamma street', 2200, 33, 51.00, 51.00),
    ('SK', 'DD', 'Delta street', 2200, 11, 54.00, 54.00),
    ('SK', 'EE', 'Epsilon street', 3300, 22, 57.00, 57.00),
    ('SK', 'FF', 'Franklin street', 3300, 33, 60.00, 60.00);

INSERT INTO milestone (planned_time, address_id) VALUES 
    ('2025-05-05 08:00:00', 1),
    ('2025-05-05 10:00:00', 2),
    ('2025-05-05 12:00:00', 3),
    ('2025-05-05 14:00:00', 4),
    ('2025-05-05 16:00:00', 5),
    ('2025-05-05 18:00:00', 6),
    ('2025-05-05 08:00:00', 7),
    ('2025-05-05 10:00:00', 8),
    ('2025-05-05 12:00:00', 9),
    ('2025-05-05 14:00:00', 10),
    ('2025-05-05 16:00:00', 11),
    ('2025-05-05 18:00:00', 12);

INSERT INTO section (order_index, start_milestone_id, end_milestone_id, transport_plan_id) VALUES
    (1, 1, 2, 1),
    (2, 3, 7, 1),
    (3, 8, 9, 1),
    (1, 4, 5, 2),
    (2, 6, 10, 2),
    (3, 11, 12, 2);






