package com.example.animetracker.controller;

import com.example.animetracker.exceptions.NegException;
import com.example.animetracker.model.Anime;
import com.example.animetracker.service.AnimeService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Este es el controlador principal de mi aplicación.
 * Aquí he puesto toda la lógica para que funcionen los botones, la tabla y los
 * cuadros donde escribimos los datos de los animes.
 */
public class AnimeController {

    @FXML private TableView<Anime> tablaAnimes;
    @FXML private TableColumn<Anime, String> colTitulo, colEstado;
    @FXML private TableColumn<Anime, Integer> colPuntuacion, colTotalEp, colVistosEp;

    @FXML private TextField txtTitulo, txtTotalEp, txtVistosEp, txtPuntuacion;
    @FXML private ComboBox<String> comboGenero, comboEstado;

    /** Objeto para conectar con la capa de servicio */
    private final AnimeService animeService = new AnimeService();

    /** Esta lista sirve para que la tabla se actualice sola al añadir o borrar */
    private ObservableList<Anime> listaAnimes = FXCollections.observableArrayList();

    /**
     * Este método se ejecuta solo al abrir la ventana.
     * Configura las columnas de la tabla y rellena los desplegables con las
     * categorías de anime y los estados.
     */
    @FXML
    public void initialize() {
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado()));
        colPuntuacion.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPuntuacion()).asObject());
        colTotalEp.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEpisodiosTotales()).asObject());
        colVistosEp.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEpisodiosVistos()).asObject());

        comboEstado.setItems(FXCollections.observableArrayList("Pendiente", "Viendo", "Completado", "Abandonado"));
        comboGenero.setItems(FXCollections.observableArrayList("Kodomo", "Shōnen", "Shōjo", "Seinen", "Josei", "Isekai", "Slice of Life", "Spokon"));

        tablaAnimes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtTitulo.setText(newSelection.getTitulo());
                comboGenero.setValue(newSelection.getGenero());
                txtTotalEp.setText(String.valueOf(newSelection.getEpisodiosTotales()));
                txtVistosEp.setText(String.valueOf(newSelection.getEpisodiosVistos()));
                txtPuntuacion.setText(String.valueOf(newSelection.getPuntuacion()));
                comboEstado.setValue(newSelection.getEstado());
            }
        });

        cargarAnimes();
    }

    /**
     * Llama al servicio para traerme todos los animes de la base de datos
     * y los pone dentro de la tabla.
     */
    private void cargarAnimes() {
        try {
            listaAnimes.setAll(animeService.listarAnimes());
            tablaAnimes.setItems(listaAnimes);
        } catch (SQLException e) {
            mostrarAlerta("Fallo al cargar", "No he podido conectar con la base de datos.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Es la función del botón Añadir.
     * Pilla los datos del formulario, comprueba que no haya fallos y los guarda.
     */
    @FXML
    private void Guardar() {
        try {
            Anime nuevo = recogerDatos(0);
            animeService.guardarAnime(nuevo);
            cargarAnimes();
            limpiarCampos();
            mostrarAlerta("¡Todo bien!", "Anime guardado en tu lista.", Alert.AlertType.INFORMATION);

        } catch (NegException e) {

            mostrarAlerta("Cuidado", e.getMessage(), Alert.AlertType.WARNING);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "En los campos de números solo puedes poner cifras.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            mostrarAlerta("Error BBDD", "Fallo al guardar en la base de datos.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Sirve para modificar un anime que ya existe.
     * Primero mira cuál has elegido y luego guarda los cambios.
     */
    @FXML
    private void Actualizar() {
        Anime sel = tablaAnimes.getSelectionModel().getSelectedItem();
        if (sel != null) {
            try {
                Anime actualizado = recogerDatos(sel.getId());
                animeService.actualizarAnime(actualizado);
                cargarAnimes();
                mostrarAlerta("Actualizado", "Los cambios se han guardado.", Alert.AlertType.INFORMATION);
            } catch (NegException e) {
                mostrarAlerta("Dato mal puesto", e.getMessage(), Alert.AlertType.WARNING);
            } catch (Exception e) {
                mostrarAlerta("Error", "No se ha podido actualizar.", Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Borra el anime que tengas seleccionado.
     * He puesto una ventana de confirmación para no borrar cosas sin querer.
     */
    @FXML
    private void Eliminar() {
        Anime seleccionado = tablaAnimes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿De verdad quieres borrar " + seleccionado.getTitulo() + "?", ButtonType.YES, ButtonType.NO);
            if (confirm.showAndWait().get() == ButtonType.YES) {
                try {
                    animeService.borrarAnime(seleccionado.getId());
                    cargarAnimes();
                } catch (SQLException e) {
                    mostrarAlerta("Error", "Fallo al intentar borrar.", Alert.AlertType.ERROR);
                }
            }
        }
    }

    /**
     * Función que he creado para leer todos los TextField a la vez.
     * @param id El id del anime (si es 0 es que es nuevo).
     * @return El objeto anime ya montado con lo que hay escrito en pantalla.
     */
    private Anime recogerDatos(int id) {
        int total = txtTotalEp.getText().isEmpty() ? 0 : Integer.parseInt(txtTotalEp.getText());
        int vistos = txtVistosEp.getText().isEmpty() ? 0 : Integer.parseInt(txtVistosEp.getText());
        int nota = txtPuntuacion.getText().isEmpty() ? 0 : Integer.parseInt(txtPuntuacion.getText());
        return new Anime(id, txtTitulo.getText(), comboGenero.getValue(), total, vistos, comboEstado.getValue(), nota);
    }

    /**
     * Me sirve para cambiar la vista y pasar a la pantalla del catálogo.
     */
    @FXML
    private void irAlCatalogo() throws IOException {
        Stage stage = (Stage) tablaAnimes.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/animetracker/catalogo-view.fxml"));
        stage.setScene(new Scene(loader.load(), 600, 450));
    }

    /**
     * Vacía todos los cuadros de texto para que podamos escribir de nuevo.
     */
    private void limpiarCampos() {
        txtTitulo.clear();
        txtTotalEp.clear();
        txtVistosEp.clear();
        txtPuntuacion.clear();
        comboGenero.setValue(null);
        comboEstado.setValue(null);
    }

    /**
     * Método que he hecho para no repetir el código de las alertas todo el rato.
     * @param titulo El título que sale arriba.
     * @param mensaje Lo que le decimos al usuario.
     * @param tipo Si es un error, un aviso o información.
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}