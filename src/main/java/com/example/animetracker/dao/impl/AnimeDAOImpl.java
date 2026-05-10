package com.example.animetracker.dao.impl;

import com.example.animetracker.dao.AnimeDAO;
import com.example.animetracker.model.Anime;
import com.example.animetracker.model.ConexionBDD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Aquí es donde escribo las consultas SQL para que mi programa pueda guardar,
 * leer, borrar o cambiar los animes en PostgreSQL.
 */
public class AnimeDAOImpl implements AnimeDAO {

    /**
     * Esta función sirve para traerse todos los animes que tengo en mi lista personal.
     * @return Una lista con todos los objetos Anime que hay en la tabla "animes".
     * @throws SQLException Por si la base de datos falla o no responde.
     */
    @Override
    public List<Anime> findAll() throws SQLException {
        List<Anime> lista = new ArrayList<>();
        String sql = "SELECT * FROM animes";
        // Uso el try-with-resources para que la conexión se cierre sola al terminar
        try (Connection conn = ConexionBDD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Voy creando los objetos Anime con lo que leo de cada fila de la tabla
                lista.add(new Anime(
                        rs.getInt("id"), rs.getString("titulo"), rs.getString("genero"),
                        rs.getInt("episodios_totales"), rs.getInt("episodios_vistos"),
                        rs.getString("estado"), rs.getInt("puntuacion")
                ));
            }
        }
        return lista;
    }

    /**
     * Sirve para meter un anime nuevo en la base de datos.
     * @param anime El anime que queremos guardar.
     * @throws SQLException Si hay algún error con el comando INSERT de SQL.
     */
    @Override
    public void guardar(Anime anime) throws SQLException {
        // Preparo la orden para insertar todos los campos, incluida la puntuación
        String sql = "INSERT INTO animes (titulo, genero, episodios_totales, episodios_vistos, estado, puntuacion) VALUES (?, ?, ?, ?, ?, ?)";
        ejecutarUpdate(sql, anime, false);
    }

    /**
     * Sirve para modificar los datos de un anime que ya teníamos guardado.
     * @param anime El anime con los datos ya cambiados.
     * @throws SQLException Si falla el comando UPDATE de SQL.
     */
    @Override
    public void actualizar(Anime anime) throws SQLException {
        // Buscamos por el ID para saber cuál hay que cambiar
        String sql = "UPDATE animes SET titulo=?, genero=?, episodios_totales=?, episodios_vistos=?, estado=?, puntuacion=? WHERE id=?";
        ejecutarUpdate(sql, anime, true);
    }

    /**
     * Para borrar un anime de la lista para siempre.
     * @param id El número ID del anime que queremos quitar.
     * @throws SQLException Si falla el comando DELETE.
     */
    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM animes WHERE id = ?";
        try (Connection conn = ConexionBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    /**
     * Este es un método que he creado yo para no escribir el mismo código
     * en "guardar" y "actualizar". Se encarga de rellenar los "?" del SQL.
     * @param sql La consulta SQL (Insert o Update).
     * @param anime El objeto con los datos.
     * @param esUpdate Un interruptor: si es true, añade el ID al final para el WHERE.
     * @throws SQLException Si algo sale mal al rellenar los datos.
     */
    private void ejecutarUpdate(String sql, Anime anime, boolean esUpdate) throws SQLException {
        try (Connection conn = ConexionBDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Rellenamos los huecos del comando SQL
            ps.setString(1, anime.getTitulo());
            ps.setString(2, anime.getGenero());
            ps.setInt(3, anime.getEpisodiosTotales());
            ps.setInt(4, anime.getEpisodiosVistos());
            ps.setString(5, anime.getEstado());
            ps.setInt(6, anime.getPuntuacion());

            // Si es un Update, necesitamos poner el ID al final para el "WHERE id = ?"
            if (esUpdate) {
                ps.setInt(7, anime.getId());
            }

            ps.executeUpdate();
        }
    }

    /**
     * Esta función es la que lee de la tabla de "catalogo_animes".
     * @return Una lista de animes sugeridos para que el usuario elija.
     * @throws SQLException Si no se puede leer la tabla del catálogo.
     */
    @Override
    public List<Anime> obtenerCatalogo() throws SQLException {
        List<Anime> lista = new ArrayList<>();
        String sql = "SELECT * FROM catalogo_animes";
        try (Connection conn = ConexionBDD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Creamos los animes con 0 vistos y estado Pendiente por defecto
                lista.add(new Anime(
                        rs.getInt("id"), rs.getString("titulo"), rs.getString("genero"),
                        rs.getInt("episodios_totales"), 0, "Pendiente", 0
                ));
            }
        }
        return lista;
    }
}