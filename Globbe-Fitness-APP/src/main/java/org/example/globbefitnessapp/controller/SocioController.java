package org.example.globbefitnessapp.controller;

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
import org.example.globbefitnessapp.database.DBConnection;
import org.example.globbefitnessapp.database.DBSchema;
import org.example.globbefitnessapp.model.Socio;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SocioController implements Initializable {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Socio, String> colApellidos;

    @FXML
    private TableColumn<Socio, String> colDni;

    @FXML
    private TableColumn<Socio, String> colEmail;

    @FXML
    private TableColumn<Socio, String> colEstado;

    @FXML
    private TableColumn<Socio, String> colFechaAlta;

    @FXML
    private TableColumn<Socio, Integer> colId;

    @FXML
    private TableColumn<Socio, Integer> colIdPlan;

    @FXML
    private TableColumn<Socio, String> colNombre;

    @FXML
    private TableColumn<Socio, String> colTelefono;

    @FXML
    private TableView<Socio> tablaSocios;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFechaAlta;

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private ComboBox<Integer> cmbIdPlan;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    private ObservableList<Socio> listaSocios;
    private FilteredList<Socio> listaFiltrada;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        actions();
        initGUI();
    }

    private void actions() {
        btnVolver.setOnAction(event -> {
            Stage stage = new Stage();

            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
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

        btnAgregar.setOnAction(event -> {

            connection = DBConnection.getConnection();

            if (camposVacios()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Campos Vacios");
                alert.setHeaderText(null);
                alert.setContentText("Por favor rellene todos los campos");
                alert.showAndWait();
                return;
            }

            String query = String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?,?)",
                    DBSchema.TAB_SOCIO, DBSchema.SOCIO_NOMBRE, DBSchema.SOCIO_APELLIDOS,
                    DBSchema.SOCIO_DNI, DBSchema.SOCIO_EMAIL, DBSchema.SOCIO_TELEFONO, DBSchema.SOCIO_FECHA_ALTA,
                    DBSchema.SOCIO_ESTADO, DBSchema.SOCIO_ID_PLAN);

            try{
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, txtNombre.getText());
                preparedStatement.setString(2, txtApellidos.getText());
                preparedStatement.setString(3, txtDni.getText());
                preparedStatement.setString(4, txtEmail.getText());
                preparedStatement.setString(5, txtTelefono.getText());
                preparedStatement.setString(6, txtFechaAlta.getText());
                preparedStatement.setString(7, cmbEstado.getValue());
                preparedStatement.setInt(8, cmbIdPlan.getValue());

                preparedStatement.executeUpdate();

                cargarSocios();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Añadir Socio");
                alert.setHeaderText(null);
                alert.setContentText("Socio agregado exitosamente");
                alert.showAndWait();

                limpiarCampos();

            }catch(SQLException e){
                System.out.println("Error al intentar agregar al nuevo socio");
                System.out.println(e.getMessage());
            }
        });

        btnLimpiar.setOnAction(event -> {
            limpiarCampos();
        });

        txtBuscar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                listaFiltrada.setPredicate(socio -> socio.getNombre().toLowerCase().contains(newValue.toLowerCase())
                                                        || socio.getDni().toLowerCase().contains(newValue.toLowerCase())
                                                        || socio.getEmail().toLowerCase().contains(newValue.toLowerCase())
                );
            }
        });
    }

    private void initGUI() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idSocio"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colFechaAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colIdPlan.setCellValueFactory(new PropertyValueFactory<>("idPlan"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        cmbIdPlan.setItems(FXCollections.observableArrayList(1, 2, 3));
        cargarSocios();

        tablaSocios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, socioSeleccionado) -> {
            if (socioSeleccionado != null) {
                txtNombre.setText(socioSeleccionado.getNombre());
                txtApellidos.setText(socioSeleccionado.getApellidos());
                txtDni.setText(socioSeleccionado.getDni());
                txtEmail.setText(socioSeleccionado.getEmail());
                txtTelefono.setText(socioSeleccionado.getTelefono());
                txtFechaAlta.setText(socioSeleccionado.getFechaAlta());
                cmbEstado.setValue(socioSeleccionado.getEstado());
                cmbIdPlan.setValue(socioSeleccionado.getIdPlan());

            }
        });
    }

    private void instances() {
        listaSocios = FXCollections.observableArrayList();
        listaFiltrada = new FilteredList<>(listaSocios);
    }

    private boolean camposVacios() {
        return txtNombre.getText().isEmpty()
                || txtApellidos.getText().isEmpty()
                || txtTelefono.getText().isEmpty()
                || txtEmail.getText().isEmpty()
                || cmbEstado.getValue() == null
                || cmbIdPlan.getValue() == null;
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellidos.clear();
        txtDni.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtFechaAlta.clear();
        txtBuscar.clear();
        cmbEstado.setValue(null);
        cmbIdPlan.setValue(null);
        tablaSocios.getSelectionModel().clearSelection();
    }

    private void cargarSocios() {
        connection = DBConnection.getConnection();
        listaSocios.clear();

        String query = String.format("SELECT * FROM %s",
                DBSchema.TAB_SOCIO);

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Socio socio = new Socio(
                        resultSet.getInt(DBSchema.SOCIO_ID),
                        resultSet.getString(DBSchema.SOCIO_NOMBRE),
                        resultSet.getString(DBSchema.SOCIO_APELLIDOS),
                        resultSet.getString(DBSchema.SOCIO_DNI),
                        resultSet.getString(DBSchema.SOCIO_EMAIL),
                        resultSet.getString(DBSchema.SOCIO_TELEFONO),
                        resultSet.getString(DBSchema.SOCIO_FECHA_ALTA),
                        resultSet.getString(DBSchema.SOCIO_ESTADO),
                        resultSet.getInt(DBSchema.SOCIO_ID_PLAN)
                );
                listaSocios.add(socio);
            }

            tablaSocios.setItems(listaFiltrada);

        }catch (SQLException e){
            System.out.println("Error al cargar socios");
            System.out.println(e.getMessage());
        }
    }
}
