package org.example.globbefitnessapp.controller.admin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.globbefitnessapp.HelloApplication;
import org.example.globbefitnessapp.dao.ClaseDAO;
import org.example.globbefitnessapp.model.Clase;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ClaseAdminController implements Initializable {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private TableColumn<Clase, Integer> colAforoMaximo;

    @FXML
    private TableColumn<Clase, String> colDescripcion;

    @FXML
    private TableColumn<Clase, String> colEstado;

    @FXML
    private TableColumn<Clase, String> colFecha;

    @FXML
    private TableColumn<Clase, String> colHora;

    @FXML
    private TableColumn<Clase, Integer> colIdClase;

    @FXML
    private TableColumn<Clase, String> colMonitor;

    @FXML
    private TableColumn<Clase, String> colNombre;

    @FXML
    private TableColumn<Clase, Integer> colSala;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TableView<Clase> tablaClases;

    @FXML
    private TextField txtAforoMaximo;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtHora;

    @FXML
    private TextField txtMonitor;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtSala;

    private ObservableList<Clase> listaClases;
    private FilteredList<Clase> listaFiltrada;
    private ClaseDAO claseDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        action();
        initGUI();
    }

    private void initGUI() {
        colIdClase.setCellValueFactory(new PropertyValueFactory<>("idClase"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colSala.setCellValueFactory(new PropertyValueFactory<>("sala"));
        colAforoMaximo.setCellValueFactory(new PropertyValueFactory<>("aforoMaximo"));
        colMonitor.setCellValueFactory(new PropertyValueFactory<>("monitor"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Completa", "Cancelada"));

        cargarClases();

        tablaClases.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtNombre.setText(newValue.getNombre());
                txtDescripcion.setText(newValue.getDescripcion());
                dpFecha.setValue(LocalDate.parse(newValue.getFecha()));
                txtHora.setText(newValue.getHora());
                txtSala.setText(newValue.getSala());
                txtAforoMaximo.setText(String.valueOf(newValue.getAforoMaximo()));
                txtMonitor.setText(newValue.getMonitor());
                cmbEstado.setValue(newValue.getEstado());
            }
        });

    }

    private void action() {
        btnVolver.setOnAction(event -> {
            Stage stage = new Stage();

            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin/admin-view.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Globbe Fitness Center - Panel de Administrador");
                stage.show();

                ((Stage)btnVolver.getScene().getWindow()).close();
            }catch (IOException e){
                System.out.println("Ha ocurrido un error");
                System.out.println(e.getMessage());
            }
        });

        btnLimpiar.setOnAction(event -> {
            limpiarCampos();
        });

        txtBuscar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                listaFiltrada.setPredicate(clase -> clase.getNombre().toLowerCase().contains(newValue.toLowerCase())
                                                        || clase.getMonitor().toLowerCase().contains(newValue.toLowerCase()));
            }
        });

        btnAgregar.setOnAction(event -> {

            if (camposVacios()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Campos Vacios");
                alert.setHeaderText(null);
                alert.setContentText("Por favor rellene todos los campos");
                alert.showAndWait();
                return;
            }

            try {
                Clase clase = new Clase(
                        txtNombre.getText(),
                        txtDescripcion.getText(),
                        String.valueOf(dpFecha.getValue()),
                        txtHora.getText(),
                        txtSala.getText(),
                        Integer.parseInt(txtAforoMaximo.getText()),
                        txtMonitor.getText(),
                        cmbEstado.getValue()
                );

                claseDAO.insertClase(clase);

                cargarClases();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Añadir Clase");
                alert.setHeaderText(null);
                alert.setContentText("Clase agregada exitosamente");
                alert.showAndWait();

                limpiarCampos();
            }catch (SQLException e){
                System.out.println("Ha ocurrido un error");
                System.out.println(e.getMessage());
            }
        });
    }

    private void instances() {
        listaClases = FXCollections.observableArrayList();
        listaFiltrada = new FilteredList<>(listaClases);
        claseDAO = new ClaseDAO();
    }

    private void cargarClases() {
        try {
            listaClases.addAll(claseDAO.getAllClases());
            tablaClases.setItems(listaFiltrada);
        }catch (Exception e){
            System.out.println("Error al obtener las clases");
            System.out.println(e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        dpFecha.setValue(null);
        txtHora.clear();
        txtSala.clear();
        txtAforoMaximo.clear();
        txtMonitor.clear();
        cmbEstado.setValue(null);
        txtBuscar.clear();

    }

    private boolean camposVacios() {
        return txtNombre.getText().isEmpty()
                || txtDescripcion.getText().isEmpty()
                || dpFecha.getValue() == null
                || txtHora.getText().isEmpty()
                || txtSala.getText().isEmpty()
                || txtAforoMaximo.getText().isEmpty()
                || txtMonitor.getText().isEmpty()
                || cmbEstado.getValue() == null;
    }
}
