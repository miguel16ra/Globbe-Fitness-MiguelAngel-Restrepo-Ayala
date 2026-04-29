package org.example.globbefitnessapp.controller.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.globbefitnessapp.HelloApplication;
import org.example.globbefitnessapp.dao.SocioDAO;
import org.example.globbefitnessapp.model.Socio;
import org.example.globbefitnessapp.model.Usuario;
import org.example.globbefitnessapp.model.UsuarioLogueado;

import java.io.IOException;
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

    private ObservableList<Socio> listaDatos;
    private SocioDAO socioDAO;
    private Socio socio;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        actions();
        initGUI();

    }

    private void initGUI() {
        cargarDatos();
    }

    private void actions() {
        btnVolver.setOnAction(event -> {
            Stage stage = new Stage();

            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("user/user-view.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Globbe Fitness Center - Panel de Usuario");
                stage.show();

                ((Stage)btnVolver.getScene().getWindow()).close();
            }catch (IOException e){
                System.out.println("Ha ocurrido un error");
                System.out.println(e.getMessage());
            }
        });
    }

    private void instances() {
        listaDatos = FXCollections.observableArrayList();
        socioDAO = new SocioDAO();
    }

    private void cargarDatos() {

        try {
            Usuario usuario = UsuarioLogueado.getUsuario();

            if (usuario != null) {

                socio = socioDAO.getSocioById(usuario.getIdUsuario());

                if (socio != null) {
                    txtNombre.setText(socio.getNombre());
                    txtApellidos.setText(socio.getApellidos());
                    txtDni.setText(socio.getDni());
                    txtEmail.setText(socio.getEmail());
                    txtTelefono.setText(socio.getTelefono());
                    txtFechaAlta.setText(socio.getFechaAlta());
                    txtEstado.setText(socio.getEstado());
                    txtPlan.setText(String.valueOf(socio.getIdPlan()));
                }
            }

        } catch (Exception e) {
            System.out.println("Error al cargar datos");
            System.out.println(e.getMessage());
        }
    }
}

