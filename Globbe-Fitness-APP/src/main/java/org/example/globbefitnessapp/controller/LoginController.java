package org.example.globbefitnessapp.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.globbefitnessapp.HelloApplication;
import org.example.globbefitnessapp.model.Socio;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button btnLogin;

    @FXML
    private TextField editCorreo;

    @FXML
    private TextField editLogin;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        actions();
    }

    private void actions() {
        btnLogin.setOnAction(event -> {
            Socio socioLogin = DataSet.get
        });




    }
}
