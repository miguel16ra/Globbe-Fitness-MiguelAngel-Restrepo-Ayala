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
import org.example.globbefitnessapp.dao.UsuarioDAO;
import org.example.globbefitnessapp.model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable {

    @FXML
    private TableColumn<Usuario, String> colApellidos;

    @FXML
    private TableColumn<Usuario, String> colCorreo;

    @FXML
    private TableColumn<Usuario, Integer> colId;

    @FXML
    private TableColumn<Usuario, String> colNombre;

    @FXML
    private TableColumn<Usuario, String> colPassword;

    @FXML
    private TableColumn<Usuario, String> colRol;

    @FXML
    private ComboBox<String> comboRol;

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPassword;

    private ObservableList<Usuario> listaUsuarios;
    private FilteredList<Usuario> listaFiltrada;
    private UsuarioDAO usuarioDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        actions();
        initGUI();
    }

    private void initGUI() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        cargarUsuarios();
        comboRol.setItems(FXCollections.observableArrayList("ADMIN", "USER"));

        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, socioSeleccionado) -> {
            if (socioSeleccionado != null) {
                txtNombre.setText(socioSeleccionado.getNombre());
                txtApellidos.setText(socioSeleccionado.getApellidos());
                txtCorreo.setText(socioSeleccionado.getCorreo());
                txtPassword.setText(socioSeleccionado.getPassword());
                comboRol.setValue(socioSeleccionado.getRol());
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

        btnActualizar.setOnAction(event -> {
            Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

            if (usuarioSeleccionado == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Actualizar usuario");
                alert.setHeaderText(null);
                alert.setContentText("Selecciona un usuario de la tabla");
                alert.showAndWait();
                return;
            }

            if (txtNombre.getText().isEmpty()
                    || txtApellidos.getText().isEmpty()
                    || txtCorreo.getText().isEmpty()
                    || txtPassword.getText().isEmpty()
                    || comboRol.getValue() == null) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Campos vacíos");
                alert.setHeaderText(null);
                alert.setContentText("Rellena todos los campos");
                alert.showAndWait();
                return;
            }

            try {
                usuarioSeleccionado.setNombre(txtNombre.getText());
                usuarioSeleccionado.setApellidos(txtApellidos.getText());
                usuarioSeleccionado.setCorreo(txtCorreo.getText());
                usuarioSeleccionado.setPassword(txtPassword.getText());
                usuarioSeleccionado.setRol(comboRol.getValue());

                usuarioDAO.updateUsuarioSocio(usuarioSeleccionado);

                cargarUsuarios();
                limpiarCampos();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Actualizar usuario");
                alert.setHeaderText(null);
                alert.setContentText("Usuario actualizado correctamente");
                alert.showAndWait();

            } catch (Exception e) {
                System.out.println("Error al actualizar usuario");
                System.out.println(e.getMessage());
            }
        });

        btnEliminar.setOnAction(event -> {
            Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

            if (usuarioSeleccionado == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Eliminar usuario");
                alert.setHeaderText(null);
                alert.setContentText("Selecciona un usuario de la tabla");
                alert.showAndWait();
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Seguro que quieres eliminar el usuario seleccionado?");

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                try {
                    usuarioDAO.deleteUsuarioSocio(usuarioSeleccionado);

                    cargarUsuarios();
                    limpiarCampos();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Eliminar usuario");
                    alert.setHeaderText(null);
                    alert.setContentText("Usuario eliminado correctamente");
                    alert.showAndWait();

                } catch (Exception e) {
                    System.out.println("Error al eliminar usuario");
                    System.out.println(e.getMessage());
                }
            }
        });

        txtBuscar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                listaFiltrada.setPredicate(usuario -> usuario.getNombre().toLowerCase().contains(newValue.toLowerCase())
                        || usuario.getRol().toLowerCase().contains(newValue.toLowerCase())
                        || usuario.getApellidos().toLowerCase().contains(newValue.toLowerCase())
                );
            }
        });

    }

    private void instances() {
        listaUsuarios = FXCollections.observableArrayList();
        listaFiltrada = new FilteredList<>(listaUsuarios);
        usuarioDAO = new UsuarioDAO();
    }

    private void cargarUsuarios() {
        listaUsuarios.clear();
        try {
            listaUsuarios.addAll(usuarioDAO.getAllUsuarios());
            tablaUsuarios.setItems(listaFiltrada);

        }catch (Exception e) {
            System.out.println("Error al cargar usuarios");
            System.out.println(e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellidos.clear();
        txtCorreo.clear();
        txtPassword.clear();
        comboRol.setValue(null);
        txtBuscar.clear();
    }
}

