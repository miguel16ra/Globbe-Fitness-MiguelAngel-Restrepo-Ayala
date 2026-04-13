package org.example.tiendaapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.tiendaapp.model.Libro;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class APPController implements Initializable {

    @FXML
    private TextField txtIdLibro;

    @FXML
    private Button btnImportar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnFavorito;

    @FXML
    private Button btnExportar;

    @FXML
    private Button btnImportarFav;

    @FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TableColumn<Libro, Long> colId;

    @FXML
    private TableColumn<Libro, String> colTitle;

    @FXML
    private TableColumn<Libro, Long> colYear;

    @FXML
    private TableColumn<Libro, String> colPublisher;

    @FXML
    private TableColumn<Libro, Long> colPages;

    @FXML
    private TextArea txtAreaInfo;

    private BibliotecaController bibliotecaController;
    private FileController fileController;
    private ObservableList<Libro> listaLibros;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instances();
        initGUI();
        actions();
    }

    private void instances() {
        bibliotecaController = new BibliotecaController();
        fileController = new FileController();
        listaLibros = FXCollections.observableArrayList();
    }

    private void initGUI() {
        tablaLibros.setItems(listaLibros);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        colPages.setCellValueFactory(new PropertyValueFactory<>("pages"));
    }

    private void actions() {

        btnImportar.setOnAction(event -> {
            bibliotecaController.cargarLibros();
            listaLibros.clear();
            listaLibros.addAll(bibliotecaController.getLibros());
            txtAreaInfo.setText("Libros importados correctamente.");
        });

        btnBuscar.setOnAction(event -> {
            if (txtIdLibro.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Buscar libro");
                alert.setContentText("Debes introducir un ID.");
                alert.show();
            } else {
                try {
                    long id = Long.parseLong(txtIdLibro.getText());
                    Libro libro = bibliotecaController.buscarLibroEnBiblioteca(id);

                    if (libro != null) {
                        txtAreaInfo.setText(
                                "Título: " + libro.getTitle() +
                                        "\nAño: " + libro.getYear() +
                                        "\nEditorial: " + libro.getPublisher() +
                                        "\nNotas: " + libro.getNotes()
                        );
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Buscar libro");
                        alert.setContentText("No se ha encontrado ningún libro con ese ID.");
                        alert.show();
                    }

                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("El ID debe ser numérico.");
                    alert.show();
                }
            }
        });

        btnFavorito.setOnAction(event -> {
            if (txtIdLibro.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Favoritos");
                alert.setContentText("Debes introducir un ID.");
                alert.show();
            } else {
                try {
                    long id = Long.parseLong(txtIdLibro.getText());
                    boolean agregado = bibliotecaController.agregarFavorito(id);

                    if (agregado) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Favoritos");
                        alert.setContentText("Libro añadido a favoritos correctamente.");
                        alert.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Favoritos");
                        alert.setContentText("No se pudo añadir el libro a favoritos.");
                        alert.show();
                    }

                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("El ID debe ser numérico.");
                    alert.show();
                }
            }
        });

        btnExportar.setOnAction(event -> {
            fileController.exportarFavoritos(bibliotecaController.getFavoritos());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exportar");
            alert.setContentText("Favoritos exportados correctamente.");
            alert.show();
        });

        btnImportarFav.setOnAction(event -> {
            List<Libro> favoritos = fileController.importarFavoritos();

            if (favoritos.isEmpty()) {
                txtAreaInfo.setText("No hay favoritos guardados.");
            } else {
                StringBuilder builder = new StringBuilder();
                builder.append("FAVORITOS IMPORTADOS:\n\n");

                for (Libro libro : favoritos) {
                    builder.append("Título: ").append(libro.getTitle()).append("\n");
                    builder.append("Año: ").append(libro.getYear()).append("\n");
                    builder.append("Editorial: ").append(libro.getPublisher()).append("\n");
                    builder.append("-------------------------\n");
                }

                txtAreaInfo.setText(builder.toString());
            }
        });
    }
}

