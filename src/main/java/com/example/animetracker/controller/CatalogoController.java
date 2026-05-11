package com.example.animetracker.controller;

import com.example.animetracker.exceptions.NegException;
import com.example.animetracker.model.Anime;
import com.example.animetracker.service.AnimeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Este es el controlador para la pantalla del catálogo.
 * Sirve para mostrar una lista de animes que ya están en la base de datos
 * para que el usuario solo tenga que elegir uno y añadirlo a su lista.
 */
public class CatalogoController {


    @FXML private TableView<Anime> tablaCatalogo;
    @FXML private TableColumn<Anime, String> colTitulo;
    @FXML private TableColumn<Anime, String> colGenero;
    @FXML private TableColumn<Anime, Integer> colEps;

    /** Conexión con el servicio para pedir los datos del catálogo */
    private final AnimeService animeService = new AnimeService();

    /**
     * Este método prepara la tabla al arrancar esta ventana.
     * Le dice a cada columna qué dato del anime tiene que mostrar y llama
     * a la función que trae los datos.
     */
    @FXML
    public void initialize() {
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colEps.setCellValueFactory(new PropertyValueFactory<>("episodiosTotales"));

        cargarDatosCatalogo();
    }

    /**
     * Se conecta con el servicio para pillar todos los animes que hay
     * en la tabla de "catalogo_animes" y los mete en la tabla.
     */
    private void cargarDatosCatalogo() {
        try {
            // Pillamos la lista del servicio y la convertimos para que JavaFX la entienda
            tablaCatalogo.setItems(FXCollections.observableArrayList(animeService.listarCatalogo()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Esta es la función del botón de añadir.
     * Mira qué anime has pinchado en la tabla y lo guarda en tu lista personal.
     * He controlado los fallos con mi propia excepción de números negativos.
     */
    @FXML
    private void añadirAMiLista() {

        Anime seleccionado = tablaCatalogo.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {

                animeService.guardarAnime(seleccionado);


                mostrarAlerta("¡Logrado!", "Se ha añadido " + seleccionado.getTitulo() + " a tu lista.", Alert.AlertType.INFORMATION);

            } catch (NegException e) {
                // Si por lo que sea el catálogo tuviera números negativos, saltaría esto
                mostrarAlerta("Validación", e.getMessage(), Alert.AlertType.WARNING);

            } catch (SQLException e) {
                // Fallo si la base de datos no responde
                mostrarAlerta("Error BBDD", "No se pudo conectar con la base de datos.", Alert.AlertType.ERROR);
            }
        } else {

            mostrarAlerta("Aviso", "Selecciona un anime del catálogo primero.", Alert.AlertType.WARNING);
        }
    }

    /**
     * Una función pequeñita que me sirve para sacar avisos por pantalla
     * sin tener que escribir todo el código del Alert cada vez.
     * * @param titulo  Lo que sale arriba de la ventanita.
     * @param mensaje El texto que lee el usuario.
     * @param tipo    Si es una información, un error o un aviso.
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Esta función es para el botón "Volver".
     * Cierra esta vista y nos carga de nuevo la pantalla principal (main-view).
     * * @throws IOException Si no encuentra el archivo del diseño principal.
     */
    @FXML
    private void volver() throws IOException {
        // Buscamos la ventana actual
        Stage stage = (Stage) tablaCatalogo.getScene().getWindow();

        // Cargamos el FXML de la pantalla principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/animetracker/main-view.fxml"));

        // Cambiamos el contenido de la ventana
        stage.setScene(new Scene(loader.load(), 600, 400));
    }
}