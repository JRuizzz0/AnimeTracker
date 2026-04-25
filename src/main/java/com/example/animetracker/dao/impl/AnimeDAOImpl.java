package com.example.animetracker.dao.impl;

import com.example.animetracker.dao.AnimeDAO;
import com.example.animetracker.model.Anime;
import com.example.animetracker.model.ConexionBDD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimeDAOImpl implements AnimeDAO {

    @Override
    public List<Anime> findAll() throws SQLException {
        List<Anime> lista = new ArrayList<>();
        String sql = "SELECT * FROM animes";
        try (Connection conn = ConexionBDD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Anime(
                        rs.getInt("id"), rs.getString("titulo"), rs.getString("genero"),
                        rs.getInt("episodios_totales"), rs.getInt("episodios_vistos"),
                        rs.getString("estado"), rs.getInt("puntuacion")
                ));
            }
        }
        return lista;
    }

    @Override
    public void guardar(Anime anime) throws SQLException {
        String sql = "INSERT INTO animes (titulo, genero, episodios_totales, episodios_vistos, estado, puntuacion) VALUES (?, ?, ?, ?, ?, ?)";
        ejecutarUpdate(sql, anime, false);
    }
    @Override
    public void actualizar(Anime anime) throws SQLException {
        String sql = "UPDATE animes SET titulo=?, genero=?, episodios_totales=?, episodios_vistos=?, estado=?, puntuacion=? WHERE id=?";
        ejecutarUpdate(sql, anime, true);
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM animes WHERE id = ?";
        try (Connection conn = ConexionBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }


    private void ejecutarUpdate(String sql, Anime anime, boolean esUpdate) throws SQLException {
        try (Connection conn = ConexionBDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, anime.getTitulo());
            ps.setString(2, anime.getGenero());
            ps.setInt(3, anime.getEpisodiosTotales());
            ps.setInt(4, anime.getEpisodiosVistos());
            ps.setString(5, anime.getEstado());
            ps.setInt(6, anime.getPuntuacion());

            if (esUpdate) {
                ps.setInt(7, anime.getId());
            }

            ps.executeUpdate();
        }
    }
}