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
import org.example.globbefitnessapp.dao.ReservaDAO;
import org.example.globbefitnessapp.model.Reserva;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReservaAdminController implements Initializable {

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
    private ComboBox<String> cmbAsistencia;

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private TableColumn<Reserva, String> colAsistencia;

    @FXML
    private TableColumn<Reserva, String> colEstado;

    @FXML
    private TableColumn<Reserva, String> colFechaReserva;

    @FXML
    private TableColumn<Reserva, Integer> colIdClase;

    @FXML
    private TableColumn<Reserva, Integer> colIdReserva;

    @FXML
    private TableColumn<Reserva, Integer> colIdSocio;

    @FXML
    private DatePicker dpFechaReserva;

    @FXML
    private TableView<Reserva> tablaReservas;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtIdClase;

    @FXML
    private TextField txtIdSocio;

    private ObservableList<Reserva> listaReservas;
    private FilteredList<Reserva> listaFiltrada;
    private ReservaDAO reservaDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        actions();
        initGUI();
    }

    private void initGUI() {
        colIdReserva.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        colFechaReserva.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colAsistencia.setCellValueFactory(new PropertyValueFactory<>("asistencia"));
        colIdSocio.setCellValueFactory(new PropertyValueFactory<>("idSocio"));
        colIdClase.setCellValueFactory(new PropertyValueFactory<>("idClase"));
        cmbEstado.setItems(FXCollections.observableArrayList("Pendiente", "Confirmada", "Cancelada"));
        cmbAsistencia.setItems(FXCollections.observableArrayList("Presente", "Ausente"));
        cargarReservas();

        tablaReservas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            if (newValue != null) {
                dpFechaReserva.setValue(LocalDate.parse(newValue.getFechaReserva()));
                cmbEstado.setValue(newValue.getEstado());
                cmbAsistencia.setValue(newValue.getAsistencia());
                txtIdSocio.setText(String.valueOf(newValue.getIdSocio()));
                txtIdClase.setText(String.valueOf(newValue.getIdClase()));
            }
        });
    }

    private void actions() {
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
                listaFiltrada.setPredicate(reserva -> reserva.getFechaReserva().toLowerCase().contains(newValue.toLowerCase())
                                                            || reserva.getEstado().toLowerCase().contains(newValue.toLowerCase())
                                                            || reserva.getAsistencia().toLowerCase().contains(newValue.toLowerCase())
                );
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
                Reserva reserva = new Reserva(
                        String.valueOf(dpFechaReserva.getValue()),
                        cmbEstado.getValue(),
                        cmbAsistencia.getValue(),
                        Integer.parseInt(txtIdSocio.getText()),
                        Integer.parseInt(txtIdClase.getText())
                );

                reservaDAO.insertReserva(reserva);

                cargarReservas();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Añadir Reserva");
                alert.setHeaderText(null);
                alert.setContentText("Reserva agregada exitosamente");
                alert.showAndWait();

                limpiarCampos();
            }catch (SQLException e){
                System.out.println("Ha ocurrido un error");
                System.out.println(e.getMessage());
            }
        });

    }

    private boolean camposVacios() {
        return dpFechaReserva.getValue() == null
                || cmbEstado.getValue() == null
                || cmbAsistencia.getValue() == null
                || txtIdSocio.getText().isEmpty()
                || txtIdClase.getText().isEmpty();
    }

    private void instances() {
        listaReservas = FXCollections.observableArrayList();
        listaFiltrada = new FilteredList<>(listaReservas);
        reservaDAO = new ReservaDAO();
    }

    private void cargarReservas() {
        listaReservas.clear();

        try {
            listaReservas.addAll(reservaDAO.getAllReservas());
            tablaReservas.setItems(listaFiltrada);
        } catch (Exception e) {
            System.out.println("Error al cargar las reservas");
            System.out.println(e.getMessage());
        }
    }

    private void limpiarCampos() {
        dpFechaReserva.setValue(null);
        cmbEstado.setValue(null);
        cmbAsistencia.setValue(null);
        txtIdSocio.clear();
        txtIdClase.clear();
        txtBuscar.clear();
    }
}

