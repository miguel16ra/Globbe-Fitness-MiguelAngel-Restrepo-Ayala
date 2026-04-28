package org.example.globbefitnessapp.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.globbefitnessapp.HelloApplication;
import org.example.globbefitnessapp.dao.UsuarioDAO;
import org.example.globbefitnessapp.model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button btnLogin;

    @FXML
    private TextField editCorreo;

    @FXML
    private TextField editPass;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        actions();
    }

    private void actions() {
        btnLogin.setOnAction(event -> {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuarioLogin = usuarioDAO.login(editCorreo.getText(), editPass.getText());

            if (usuarioLogin != null) {
                Stage stage = new Stage();

                try {
                    FXMLLoader loader;
                    switch (usuarioLogin.getRol().toLowerCase()) {
                        case "admin" ->{
                            loader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
                            Scene scene = new Scene(loader.load());
                            stage.setScene(scene);
                            stage.setTitle("Globbe Fitness Center - Panel de Administrador");
                            stage.show();

                            ((Stage) btnLogin.getScene().getWindow()).close();
                        }
                        case "user" ->{
                            loader = new FXMLLoader(HelloApplication.class.getResource("user-view.fxml"));
                            Scene scene = new Scene(loader.load());
                            stage.setScene(scene);
                            stage.setTitle("Globbe Fitness Center - Panel de Socio");
                            stage.show();

                            ((Stage) btnLogin.getScene().getWindow()).close();
                        }
                        default ->{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Rol de usuario no valido");
                            alert.show();
                        }
                    }


                }catch (IOException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("La pantalla que se intenta cargar no esta disponible");
                    alert.show();

                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Datos incorrectos");
                alert.show();
            }
        });
    }
}
