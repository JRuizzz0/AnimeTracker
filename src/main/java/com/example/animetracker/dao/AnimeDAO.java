package com.example.animetracker.dao;

import com.example.animetracker.model.Anime;
import java.sql.SQLException;
import java.util.List;

public interface AnimeDAO {
    List<Anime> findAll() throws SQLException;
    void guardar(Anime anime) throws SQLException;
    void actualizar(Anime anime) throws SQLException;
    void eliminar(int id) throws SQLException;
}