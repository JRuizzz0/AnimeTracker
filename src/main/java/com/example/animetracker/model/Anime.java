package com.example.animetracker.model;

/**
 Clase Anime
 */
public class Anime {

    // Atributos
    private int id;
    private String titulo;
    private String genero;
    private int episodiosTotales;
    private int episodiosVistos;
    private String estado;
    private int puntuacion;

    /**
     * Este es el constructor. Sirve para crear un objeto Anime nuevo
     * pasándole todos sus datos de golpe.
     * * @param id El número de identificación en la base de datos.
     * @param titulo Cómo se llama el anime.
     * @param genero El tipo de anime (Shonen, Seinen, etc.).
     * @param episodiosTotales Cuántos episodios tiene en total.
     * @param episodiosVistos Cuántos capítulos nos hemos tragado ya.
     * @param estado Si lo estamos viendo, está terminado o pendiente.
     * @param puntuacion La nota que le damos (del 0 al 10).
     */
    public Anime(int id, String titulo, String genero, int episodiosTotales, int episodiosVistos, String estado, int puntuacion) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.episodiosTotales = episodiosTotales;
        this.episodiosVistos = episodiosVistos;
        this.estado = estado;
        this.puntuacion = puntuacion;
    }

    /** @return El ID del anime en la tabla */
    public int getId() {
        return id;
    }

    /** @param id El nuevo ID que le queremos poner */
    public void setId(int id) {
        this.id = id;
    }

    /** @return El nombre del anime */
    public String getTitulo() {
        return titulo;
    }

    /** @param titulo El nombre que queremos guardar */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /** @return El género del anime */
    public String getGenero() {
        return genero;
    }

    /** @param genero El género que le queremos asignar */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /** @return El número total de episodios que existen */
    public int getEpisodiosTotales() {
        return episodiosTotales;
    }

    /** @param episodiosTotales La cantidad de episodios totales */
    public void setEpisodiosTotales(int episodiosTotales) {
        this.episodiosTotales = episodiosTotales;
    }

    /** @return Los episodios que ya hemos visto */
    public int getEpisodiosVistos() {
        return episodiosVistos;
    }

    /** @param episodiosVistos El número de episodios que llevamos vistos */
    public void setEpisodiosVistos(int episodiosVistos) {
        this.episodiosVistos = episodiosVistos;
    }

    /** @return El estado actual (Viendo, Terminado...) */
    public String getEstado() {
        return estado;
    }

    /** @param estado El estado que queremos ponerle */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /** @return La nota que le hemos puesto */
    public int getPuntuacion() {
        return puntuacion;
    }

    /** @param puntuacion La nota nueva para el anime */
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}