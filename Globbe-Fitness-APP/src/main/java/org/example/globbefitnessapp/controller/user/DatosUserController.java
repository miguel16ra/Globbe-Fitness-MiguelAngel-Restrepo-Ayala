package org.example.globbefitnessapp.controller.user;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DatosUserController implements Initializable {

    @FXML
    private Button btnRefrescar;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtFechaAlta;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPlan;

    @FXML
    private TextField txtTelefono;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        actions();
        initGUI();

    }

    private void initGUI() {

    }

    private void actions() {

    }

    private void instances() {

    }
}

