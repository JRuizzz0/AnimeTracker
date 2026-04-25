package com.example.animetracker.service;

import com.example.animetracker.dao.AnimeDAO;
import com.example.animetracker.dao.impl.AnimeDAOImpl;
import com.example.animetracker.model.Anime;

import java.sql.SQLException;
import java.util.List;
public class AnimeService {

    private final AnimeDAO animeDAO = new AnimeDAOImpl();

    public List<Anime> listarAnimes() throws SQLException {
        return animeDAO.findAll();
    }


    public void guardarAnime(Anime anime) throws SQLException {
        if (anime.getTitulo() == null || anime.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título del anime no puede estar vacío.");
        }
        animeDAO.guardar(anime);
    }
    public void actualizarAnime(Anime anime) throws SQLException {
        animeDAO.actualizar(anime);
    }
    public void borrarAnime(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID no válido para eliminación.");
        }
        animeDAO.eliminar(id);
    }
}