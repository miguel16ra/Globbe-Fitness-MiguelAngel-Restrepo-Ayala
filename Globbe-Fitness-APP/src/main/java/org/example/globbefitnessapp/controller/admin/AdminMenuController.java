package org.example.globbefitnessapp.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.globbefitnessapp.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnProductos;

    @FXML
    private Button btnReservas;

    @FXML
    private Button btnSocios;

    @FXML
    private Button btnClases;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actions();
    }

    private void actions() {
        btnLogOut.setOnAction(event -> {
            Stage stage = new Stage();
            try{
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Globbe Fitness Center");
                stage.show();

                ((Stage) btnLogOut.getScene().getWindow()).close();
            }catch (IOException e){
                System.out.println("Ha ocurrido un error");
                System.out.println(e.getMessage());
            }
        });

        btnSocios.setOnAction(event -> {
            Stage stage = new Stage();

            try{
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin/socio-view.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Globbe Fitness Center - Gestión de Socios");
                stage.show();

                ((Stage) btnSocios.getScene().getWindow()).close();
            }catch (IOException e){
                System.out.println("Ha ocurrido un error");
                System.out.println(e.getMessage());
            }
        });

        btnProductos.setOnAction(event -> {
            Stage stage = new Stage();

            try{
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin/producto-view.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Globbe Fitness Center - Gestión de Productos");
                stage.show();

                ((Stage) btnSocios.getScene().getWindow()).close();
            }catch (IOException e){
                System.out.println("Ha ocurrido un error");
                System.out.println(e.getMessage());
            }
        });

        btnReservas.setOnAction(event -> {
            Stage stage = new Stage();

            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin/reservaAdmin-view.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Globbe Fitness Center - Gestión de Reservas");
                stage.show();

                ((Stage) btnReservas.getScene().getWindow()).close();
            }catch (IOException e){
                System.out.println("Ha ocurrido un error");
                System.out.println(e.getMessage());
            }
        });

        btnClases.setOnAction(event -> {
            Stage stage = new Stage();

            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin/clasesAdmin-view.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Globbe Fitness Center - Gestión de Clases");
                stage.show();

                ((Stage) btnClases.getScene().getWindow()).close();
            }catch (IOException e){
                System.out.println("Ha ocurrido un error");
                System.out.println(e.getMessage());
            }

        });
    }
}
