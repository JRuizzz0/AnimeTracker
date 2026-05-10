package com.example.animetracker.dao;

import com.example.animetracker.model.Anime;
import java.sql.SQLException;
import java.util.List;

/**
 * Esta es la interfaz de mi DAO.
 */
public interface AnimeDAO {

    /**
     * Sirve para traerme todos los animes que tengo guardados en mi lista personal.
     * @return Una lista con todos los animes que hay en la tabla principal.
     * @throws SQLException Por si hay algún fallo con el SQL o la conexión.
     */
    List<Anime> findAll() throws SQLException;

    /**
     * Sirve para sacar la lista de animes que ya vienen predefinidos en la
     * tabla de catálogo.
     * @return La lista de animes que salen como sugerencias.
     * @throws SQLException Por si no se puede leer la tabla del catálogo en PostgreSQL.
     */
    List<Anime> obtenerCatalogo() throws SQLException;

    /**
     * Se usa para meter un anime nuevo en la base de datos.
     * @param anime El objeto con toda la información del anime que queremos guardar.
     * @throws SQLException Si falla el comando de insertar los datos.
     */
    void guardar(Anime anime) throws SQLException;

    /**
     * Se usa para cambiar los datos de un anime que ya estaba en la lista.
     * @param anime El anime con los nuevos datos que queremos actualizar.
     * @throws SQLException Si falla la actualización en la base de datos.
     */
    void actualizar(Anime anime) throws SQLException;

    /**
     * Se usa para borrar un anime de la lista usando su número de ID.
     * @param id El número identificador del anime que queremos quitar de la tabla.
     * @throws SQLException Si hay un error al intentar borrar el registro.
     */
    void eliminar(int id) throws SQLException;
}