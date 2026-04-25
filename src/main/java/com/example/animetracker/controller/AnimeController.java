package com.example.animetracker.controller;

import com.example.animetracker.model.Anime;
import com.example.animetracker.service.AnimeService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class AnimeController {

    @FXML private TableView<Anime> tablaAnimes;
    @FXML private TableColumn<Anime, String> colTitulo;
    @FXML private TableColumn<Anime, String> colEstado;
    @FXML private TableColumn<Anime, Integer> colPuntuacion;
    @FXML private TableColumn<Anime, Integer> colTotalEp;
    @FXML private TableColumn<Anime, Integer> colVistosEp;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtGenero;
    @FXML private TextField txtTotalEp;
    @FXML private TextField txtVistosEp;
    @FXML private TextField txtPuntuacion;
    @FXML private ComboBox<String> comboEstado;

    private final AnimeService animeService = new AnimeService();
    private ObservableList<Anime> listaAnimes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado()));
        colPuntuacion.setCellValueFactory(new PropertyValueFactory<>("puntuacion"));
        comboEstado.setItems(FXCollections.observableArrayList("Pendiente", "Viendo", "Completado", "Abandonado"));
        colTotalEp.setCellValueFactory(new PropertyValueFactory<>("episodiosTotales"));
        colVistosEp.setCellValueFactory(new PropertyValueFactory<>("episodiosVistos"));


        tablaAnimes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtTitulo.setText(newSelection.getTitulo());
                txtGenero.setText(newSelection.getGenero());
            }
        });

        cargarAnimes();
    }

    private void cargarAnimes() {
        try {
            listaAnimes.clear();
            List<Anime> animes = animeService.listarAnimes();
            listaAnimes.addAll(animes);
            tablaAnimes.setItems(listaAnimes);
        } catch (SQLException e) {
            mostrarAlerta("Error de carga", "No se pudieron obtener los datos.");
        }
    }

    @FXML
    private void Guardar() {
        try {

            String titulo = txtTitulo.getText();
            String genero = txtGenero.getText();
            int totalEp = Integer.parseInt(txtTotalEp.getText());
            int vistos = Integer.parseInt(txtVistosEp.getText());
            String estado = comboEstado.getValue();
            int nota = Integer.parseInt(txtPuntuacion.getText());
            Anime nuevo = new Anime(0, titulo, genero, totalEp, vistos, estado, nota);
            animeService.guardarAnime(nuevo);

            cargarAnimes();
            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de datos", "Por favor, introduce números válidos en episodios y nota.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Eliminar() {
        Anime seleccionado = tablaAnimes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                animeService.borrarAnime(seleccionado.getId());
                cargarAnimes();
            } catch (SQLException e) {
                mostrarAlerta("Error al eliminar", "No se pudo borrar el registro.");
            }
        }
    }

    @FXML
    private void Actualizar() {
        Anime sel = tablaAnimes.getSelectionModel().getSelectedItem();

        if (sel != null) {
            try {
                int total = txtTotalEp.getText().isEmpty() ? 0 : Integer.parseInt(txtTotalEp.getText());
                int vistos = txtVistosEp.getText().isEmpty() ? 0 : Integer.parseInt(txtVistosEp.getText());
                int nota = txtPuntuacion.getText().isEmpty() ? 0 : Integer.parseInt(txtPuntuacion.getText());

                sel.setTitulo(txtTitulo.getText());
                sel.setGenero(txtGenero.getText());
                sel.setEpisodiosTotales(total);
                sel.setEpisodiosVistos(vistos);
                sel.setEstado(comboEstado.getValue());
                sel.setPuntuacion(nota);

                animeService.actualizarAnime(sel);
                cargarAnimes();

            } catch (NumberFormatException e) {
                System.out.println("Error: Has puesto letras donde van números.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void limpiarCampos() {
        txtTitulo.clear();
        txtGenero.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}