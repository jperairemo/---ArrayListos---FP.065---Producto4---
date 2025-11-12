-- ============================================
-- Reset (solo para desarrollo, opcional)
-- ============================================
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE alquiler_extra;
TRUNCATE TABLE alquiler;
TRUNCATE TABLE extra;
TRUNCATE TABLE vehiculo;
TRUNCATE TABLE cliente;
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- CLIENTES
-- ============================================
INSERT INTO cliente (id, nombre, apellidos, email, dni, telefono) VALUES
                                                                      (1,'Juan','Pérez','juan@example.com','12345678A','612345678'),
                                                                      (2,'María','García','maria@example.com','87654321B','623456789'),
                                                                      (3,'Carlos','López','carlos@example.com','11223344C','634567890');

-- ============================================
-- VEHICULOS
-- Nota: disponible -> 1/0 porque la columna es BIT
-- ============================================
INSERT INTO vehiculo (id, matricula, marca, modelo, tipo, precio_dia, disponible) VALUES
                                                                                      (1,'1234ABC','BMW','Serie 3','Sedán',45, 1),
                                                                                      (2,'5678DEF','Mercedes','Clase A','Compacto',40, 1),
                                                                                      (3,'9012GHI','Audi','A4','Sedán',50, 0),
                                                                                      (4,'3456JKL','Volkswagen','Golf','Compacto',35, 1);

-- ============================================
-- EXTRAS
-- ============================================
INSERT INTO extra (id, nombre, precio) VALUES
                                           (1,'GPS',5),
                                           (2,'Silla para niños',8),
                                           (3,'Seguro premium',15),
                                           (4,'Conductor adicional',10);

-- ============================================
-- ALQUILERES
-- (En MySQL puedes usar directamente fechas 'YYYY-MM-DD')
-- ============================================
INSERT INTO alquiler (id, cliente_id, vehiculo_id, fecha_inicio, fecha_fin, precio_total, estado) VALUES
                                                                                                      (1,1,1,'2025-10-20','2025-10-25',225,'Activo'),
                                                                                                      (2,2,2,'2025-10-22','2025-10-27',250,'Activo'),
                                                                                                      (3,3,4,'2025-10-15','2025-10-18',120,'Completado');

-- ============================================
-- Relación alquiler-extra
-- ============================================
INSERT INTO alquiler_extra (alquiler_id, extra_id) VALUES
                                                       (1,1),(1,3),
                                                       (2,2),
                                                       (3,4);

-- ============================================
-- Ajustar AUTO_INCREMENT (estética; no necesario si usas TRUNCATE arriba)
-- ============================================
ALTER TABLE cliente  AUTO_INCREMENT = 4;
ALTER TABLE vehiculo AUTO_INCREMENT = 5;
ALTER TABLE extra    AUTO_INCREMENT = 5;
ALTER TABLE alquiler AUTO_INCREMENT = 4;
