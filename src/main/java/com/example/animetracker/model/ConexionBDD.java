package com.example.animetracker.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase es el "puente" que une Java con la base de datos PostgreSQL.
 */
public class ConexionBDD {

    /** La dirección donde está mi base de datos en el ordenador */
    private static final String URL = "jdbc:postgresql://localhost:5432/Anime_tracker";

    /** Mi nombre de usuario de PostgreSQL */
    private static final String USER = "postgres";

    /** La contraseña que puse al instalar la base de datos */
    private static final String PASSWORD = "1234";

    /**
     * * @return El objeto Connection que nos permite enviarle comandos SQL a la base de datos.
     * @throws SQLException Si los datos están mal puestos o la base de datos está apagada.
     */
    public static Connection getConnection() throws SQLException {
        // DriverManager se encarga de usar los datos de arriba para conectar
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}