/* crear base de datos */
CREATE DATABASE batalla_db;

USE batalla_db;


/* Limpiar las tablas por si existen */
DROP TABLE IF EXISTS historial_batalla;
DROP TABLE IF EXISTS batallas;
DROP TABLE IF EXISTS personajes;

/* creacion de las dos tablas */

CREATE TABLE personajes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apodo VARCHAR(100) UNIQUE NOT NULL,
    tipo ENUM('HEROE','VILLANO') NOT NULL,
    vida INT NOT NULL,
    fuerza INT NOT NULL,
    defensa INT NOT NULL,
    bendicion INT NOT NULL,
    victorias INT DEFAULT 0
);
CREATE TABLE batallas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    heroe_id INT NOT NULL,
    villano_id INT NOT NULL,
    ganador_id INT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (heroe_id) REFERENCES personajes(id),
    FOREIGN KEY (villano_id) REFERENCES personajes(id),
    FOREIGN KEY (ganador_id) REFERENCES personajes(id)
);
CREATE TABLE historial_batalla (
    id INT AUTO_INCREMENT PRIMARY KEY,
    batalla_id INT NOT NULL,
    turno INT NOT NULL,
    descripcion TEXT NOT NULL,
    FOREIGN KEY (batalla_id) REFERENCES batallas(id) ON DELETE CASCADE
);

-- Insertar personajes de prueba
INSERT INTO personajes (nombre, apodo, tipo, vida, fuerza, defensa, bendicion, victorias)
VALUES 
    ('Arthas',   'El Luminoso',    'HEROE',   130, 22, 10, 65, 0),
    ('Diablo',   'El Oscuro',      'VILLANO', 150, 20,  8, 45, 0),
    ('Galadriel','La Eterna',      'HEROE',   120, 18, 12, 90, 0),
    ('Sauron',   'El Señor Oscuro','VILLANO', 160, 25,  9, 40, 0),
    ('Leonidas', 'El Espartano',   'HEROE',   145, 24, 13, 55, 0),
    ('Morgoth',  'El Primero',     'VILLANO', 155, 23,  8, 35, 0),
    ('Aragorn',  'El Heredero',    'HEROE',   135, 21, 11, 70, 0),
    ('Maleficent','La Maldicion',  'VILLANO', 140, 19, 10, 80, 0);
