-- Datos de prueba para order-service
-- Solo se insertan si la tabla está vacía

INSERT INTO orders (user_id, product, quantity, unit_price, total, status, created_at, updated_at)
SELECT * FROM (VALUES
    (1, 'Laptop Lenovo IdeaPad 5',    1, 3500000.00, 3500000.00, 'CONFIRMED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'Mouse Logitech MX Master 3', 1,  450000.00,  450000.00, 'DELIVERED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Teclado mecánico Corsair',   1,  650000.00,  650000.00, 'SHIPPED',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'Monitor Samsung 27" 4K',     1, 1800000.00, 1800000.00, 'PENDING',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 'Audífonos Sony WH-1000XM5',  1, 1500000.00, 1500000.00, 'CONFIRMED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
) AS new_orders(user_id, product, quantity, unit_price, total, status, created_at, updated_at)
WHERE NOT EXISTS (SELECT 1 FROM orders);
