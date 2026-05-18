package com.example.animetracker.service;

import com.example.animetracker.dao.AnimeDAO;
import com.example.animetracker.dao.impl.AnimeDAOImpl;
import com.example.animetracker.exceptions.NegException;
import com.example.animetracker.model.Anime;

import java.sql.SQLException;
import java.util.List;

/**
 Service Anime
 */
public class AnimeService {

    /** Objeto para usar las funciones de la base de datos que escribí en el DAO */
    private final AnimeDAO animeDAO = new AnimeDAOImpl();

    /**
     * Le pide al DAO que nos dé todos los animes de nuestra lista personal.
     * @return La lista de animes que tenemos guardados.
     * @throws SQLException Si hay algún problema al leer de la tabla.
     */
    public List<Anime> listarAnimes() throws SQLException {
        return animeDAO.findAll();
    }

    /**
     * Le pide al DAO los animes que hay en la tabla de catálogo
     * @return La lista de animes del catálogo.
     * @throws SQLException Si la base de datos no responde.
     */
    public List<Anime> listarCatalogo() throws SQLException {
        return animeDAO.obtenerCatalogo();
    }

    /**
     * Sirve para guardar un anime. Antes de llamar al DAO, pasa por mi
     * función de validar para ver si el usuario ha puesto algún número negativo.
     * @param anime El anime que queremos guardar.
     * @throws SQLException Por fallos de conexión.
     * @throws NegException Si el usuario ha puesto datos que no tienen sentido.
     */
    public void guardarAnime(Anime anime) throws SQLException, NegException {
        validarAnime(anime); // Pasamos por el filtro de seguridad
        animeDAO.guardar(anime);
    }

    /**
     * Sirve para actualizar un anime. También comprueba los datos antes
     * de hacer el cambio definitivo en la base de datos.
     * @param anime El anime con los cambios hechos.
     * @throws SQLException Si falla la base de datos.
     * @throws NegException Si se intentan poner números negativos en la actualización.
     */
    public void actualizarAnime(Anime anime) throws SQLException, NegException {
        validarAnime(anime); // Volvemos a pasar por el filtro
        animeDAO.actualizar(anime);
    }

    /**
     * Se encarga de borrar un anime por su ID, pero primero mira si
     * el número que le pasamos es válido.
     * @param id El número identificador del anime.
     * @throws SQLException Si hay un error al borrar en PostgreSQL.
     */
    public void borrarAnime(int id) throws SQLException {
        if (id <= 0) throw new IllegalArgumentException("El ID no puede ser cero o negativo.");
        animeDAO.eliminar(id);
    }

    /**
     * Mira si el título está vacío, si los episodios son negativos o si
     * has visto más capítulos de los que existen. Si algo está mal,
     * lanza mi excepción personalizada.
     * @param anime El objeto que queremos revisar.
     * @throws NegException El error que avisa de qué dato está mal puesto.
     */
    private void validarAnime(Anime anime) throws NegException {
        // Miramos que los números no sean negativos
        if (anime.getEpisodiosTotales() < 0 || anime.getEpisodiosVistos() < 0 || anime.getPuntuacion() < 0) {
            throw new NegException("¡Error! No se permiten números negativos en episodios o nota.");
        }

        // Comprobamos que no se hayan visto más de los que hay
        if (anime.getEpisodiosVistos() > anime.getEpisodiosTotales()) {
            throw new NegException("No puedes haber visto más episodios de los que existen.");
        }

        // El título no puede estar en blanco
        if (anime.getTitulo() == null || anime.getTitulo().isBlank()) {
            throw new NegException("El título es obligatorio para poder guardar.");
        }
    }
}