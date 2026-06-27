-- Datos de prueba para user-service
-- Solo se insertan si la tabla está vacía (controlado por la aplicación)

INSERT INTO users (name, email, phone, address, created_at, updated_at)
SELECT * FROM (VALUES
    ('María González',  'maria.gonzalez@example.com',  '+57 311 555 1001', 'Calle 10 #45-20, Bogotá',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Carlos Ramírez',  'carlos.ramirez@example.com',  '+57 312 555 1002', 'Carrera 15 #32-10, Medellín',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Ana Pérez',       'ana.perez@example.com',       '+57 313 555 1003', 'Av. 5 #12-34, Cali',          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Luis Rodríguez',  'luis.rodriguez@example.com',  '+57 314 555 1004', 'Calle 7 Sur #50-22, Bogotá',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Sofía Martínez',  'sofia.martinez@example.com',  '+57 315 555 1005', 'Carrera 43A #1-50, Medellín', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
) AS new_users(name, email, phone, address, created_at, updated_at)
WHERE NOT EXISTS (SELECT 1 FROM users);
