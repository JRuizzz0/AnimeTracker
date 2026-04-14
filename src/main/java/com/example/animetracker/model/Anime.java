package com.example.animetracker.model;

public class Anime {

        private int id;
        private String titulo;
        private String genero;
        private int episodiosTotales;
        private int episodiosVistos;
        private String estado;
        private int puntuacion;

    public Anime(int id, String titulo, String genero, int episodiosTotales, int episodiosVistos, String estado, int puntuacion) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.episodiosTotales = episodiosTotales;
        this.episodiosVistos = episodiosVistos;
        this.estado = estado;
        this.puntuacion = puntuacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEpisodiosTotales() {
        return episodiosTotales;
    }

    public void setEpisodiosTotales(int episodiosTotales) {
        this.episodiosTotales = episodiosTotales;
    }

    public int getEpisodiosVistos() {
        return episodiosVistos;
    }

    public void setEpisodiosVistos(int episodiosVistos) {
        this.episodiosVistos = episodiosVistos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
