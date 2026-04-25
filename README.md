Scripts base de datos: 


create database Anime_tracker;

-- 1. Crear la tabla principal
CREATE TABLE animes (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    genero VARCHAR(50),
    episodios_totales INT NOT NULL,
    episodios_vistos INT DEFAULT 0,
    estado VARCHAR(20) DEFAULT 'Pendiente',
    puntuacion INT DEFAULT 0
);

-- 2. Insertar los datos de prueba
INSERT INTO animes (titulo, genero, episodios_totales, episodios_vistos, estado, puntuacion) 
VALUES 
('Frieren', 'Fantasia', 28, 28, 'Completado', 10),
('Kaiju No. 8', 'Shonen', 12, 1, 'Viendo', 0);
