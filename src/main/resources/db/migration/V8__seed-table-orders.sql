INSERT INTO orders (status, opened_at, closed_at, payment_form, customer_id)
VALUES
    ('Em andamento', '2023-01-01 12:00:00', NULL, 'Cartão de crédito', 1),
    ('Concluído', '2023-01-02 15:30:00', '2023-01-02 16:15:00', 'Dinheiro', 3),
    ('Em andamento', '2023-01-03 10:45:00', NULL, 'Cartão de débito', 5),
    ('Concluído', '2023-01-04 18:20:00', '2023-01-04 19:05:00', 'Pix', 7),
    ('Em andamento', '2023-01-05 14:00:00', NULL, 'Dinheiro', 9),
    ('Concluído', '2023-01-06 20:10:00', '2023-01-06 20:45:00', 'Cartão de crédito', 2),
    ('Em andamento', '2023-01-07 17:30:00', NULL, 'Cartão de débito', 4),
    ('Concluído', '2023-01-08 12:40:00', '2023-01-08 13:25:00', 'Pix', 6),
    ('Em andamento', '2023-01-09 19:15:00', NULL, 'Dinheiro', 8),
    ('Concluído', '2023-01-10 16:55:00', '2023-01-10 17:30:00', 'Cartão de crédito', 10);