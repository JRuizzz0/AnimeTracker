#Scripts Base de Datos
CREATE TABLE animes (
    id SERIAL PRIMARY KEY,             
    titulo VARCHAR(150) NOT NULL,
    genero VARCHAR(100),
    episodios_totales INT NOT NULL DEFAULT 0,
    episodios_vistos INT DEFAULT 0,
    estado VARCHAR(50) DEFAULT 'Pendiente',
    puntuacion INT DEFAULT 0
);

INSERT INTO animes (titulo, genero, episodios_totales, episodios_vistos, estado, puntuacion) 
VALUES 
('Frieren', 'Fantasía', 28, 28, 'Completado', 10),
('One Piece', 'Aventura', 1100, 500, 'Viendo', 9),
('Solo Leveling', 'Acción', 12, 0, 'Pendiente', 8),
('Cowboy Bebop', 'Sci-Fi', 26, 26, 'Completado', 10);


select * from animes
