package com.example.animetracker.controller;

import com.example.animetracker.model.Anime;
import com.example.animetracker.model.ConexionBDD;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class AnimeController {

    @FXML private TableView<Anime> tablaAnimes;
    @FXML private TableColumn<Anime, String> colTitulo;
    @FXML private TableColumn<Anime, String> colEstado;
    @FXML private TableColumn<Anime, Integer> colPuntuacion;

    @FXML private TextField txtTitulo;
    @FXML private TextField txtGenero;

    private ObservableList<Anime> listaAnimes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado()));
        colPuntuacion.setCellValueFactory(new PropertyValueFactory<>("puntuacion"));

        cargarAnimes();
    }

    private void cargarAnimes() {
        listaAnimes.clear();
        String query = "SELECT * FROM animes";
        try (Connection conn = ConexionBDD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                listaAnimes.add(new Anime(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("genero"),
                        rs.getInt("episodios_totales"),
                        rs.getInt("episodios_vistos"),
                        rs.getString("estado"),
                        rs.getInt("puntuacion")
                ));
            }
            tablaAnimes.setItems(listaAnimes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Guardar() {
        String query = "INSERT INTO animes (titulo, genero, episodios_totales) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, txtTitulo.getText());
            pstmt.setString(2, txtGenero.getText());
            pstmt.setInt(3, 12);

            pstmt.executeUpdate();
            cargarAnimes();
            txtTitulo.clear();
            txtGenero.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void Eliminar() {
        Anime seleccionado = tablaAnimes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            String query = "DELETE FROM animes WHERE id = ?";
            try (Connection conn = ConexionBDD.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, seleccionado.getId());
                pstmt.executeUpdate();
                cargarAnimes();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void Actualizar() {
        Anime seleccionado = tablaAnimes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            String query = "UPDATE animes SET titulo = ?, genero = ? WHERE id = ?";

            try (Connection conn = ConexionBDD.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, txtTitulo.getText());
                pstmt.setString(2, txtGenero.getText());
                pstmt.setInt(3, seleccionado.getId());

                pstmt.executeUpdate();
                cargarAnimes();

                txtTitulo.clear();
                txtGenero.clear();
                tablaAnimes.getSelectionModel().clearSelection();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}