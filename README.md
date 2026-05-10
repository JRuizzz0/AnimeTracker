# Scripts Base de Datos
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

-- Crear la tabla de catálogo
CREATE TABLE catalogo_animes (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    genero VARCHAR(100),
    episodios_totales INT NOT NULL
);

-- Insertar algunos animes ya preestablecidos
INSERT INTO catalogo_animes (titulo, genero, episodios_totales) VALUES 
('Dragon Ball Z', 'Shonen', 291),
('Naruto Shippuden', 'Aventura', 500),
('Spy x Family', 'Comedia', 25),
('Death Note', 'Misterio', 37),
('Bleach', 'Acción', 366);
-- Insertar una buena variedad de animes al catálogo
INSERT INTO catalogo_animes (titulo, genero, episodios_totales) VALUES 
('One Piece', 'Shōnen', 1100),
('Fullmetal Alchemist: Brotherhood', 'Shōnen', 64),
('Demon Slayer: Kimetsu no Yaiba', 'Shōnen', 55),
('Haikyuu!!', 'Spokon', 85),
('Kuroko no Basket', 'Spokon', 75),
('Steins;Gate', 'Seinen', 24),
('Monster', 'Seinen', 74),
('Mushoku Tensei', 'Isekai', 48),
('That Time I Got Reincarnated as a Slime', 'Isekai', 60),
('Your Lie in April', 'Slice of Life', 22),
('Clannad', 'Slice of Life', 47),
('Sailor Moon', 'Shōjo', 200),
('Fruits Basket', 'Shōjo', 63),
('Doraemon', 'Kodomo', 1787),
('Shin-chan', 'Kodomo', 1200);
INSERT INTO catalogo_animes (titulo, genero, episodios_totales) VALUES 
-- Seinen (Más maduros)
('Vinland Saga', 'Seinen', 48),
('Cowboy Bebop', 'Seinen', 26),
('Tokyo Ghoul', 'Seinen', 48),
('Berserk', 'Seinen', 25),

-- Josei (Público femenino adulto)
('Nana', 'Josei', 47),
('Chihayafuru', 'Josei', 74),
('Princess Jellyfish', 'Josei', 11),

-- Isekai (Mundos paralelos)
('Overlord', 'Isekai', 52),
('Re:Zero', 'Isekai', 50),
('No Game No Life', 'Isekai', 12),
('The Rising of the Shield Hero', 'Isekai', 50),

-- Slice of Life (Vida cotidiana)
('K-On!', 'Slice of Life', 39),
('March Comes in Like a Lion', 'Slice of Life', 44),
('Blue Period', 'Slice of Life', 12),

-- Spokon (Deportes)
('Blue Lock', 'Spokon', 24),
('Slam Dunk', 'Spokon', 101),
('Free!', 'Spokon', 37),

-- Shōjo (Romance/Aventura juvenil)
('Cardcaptor Sakura', 'Shōjo', 70),
('Kimi ni Todoke', 'Shōjo', 38),

-- Kodomo (Para niños)
('Pokémon', 'Kodomo', 1200);
