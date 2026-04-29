package org.example.globbefitnessapp.controller.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.globbefitnessapp.HelloApplication;
import org.example.globbefitnessapp.dao.ClaseDAO;
import org.example.globbefitnessapp.dao.ReservaDAO;
import org.example.globbefitnessapp.dao.SocioDAO;
import org.example.globbefitnessapp.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReservasUserController implements Initializable {

    @FXML
    private Button btnReservar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Clase, Integer> colAforoMaximo;

    @FXML
    private TableColumn<Clase, String> colClase;

    @FXML
    private TableColumn<ReservaTabla, String> colClaseReserva;

    @FXML
    private TableColumn<Clase, String> colDescripcion;

    @FXML
    private TableColumn<Clase, String> colEstadoClase;

    @FXML
    private TableColumn<ReservaTabla, String> colEstadoReserva;

    @FXML
    private TableColumn<Clase, String> colFechaClase;

    @FXML
    private TableColumn<ReservaTabla, String> colFechaReserva;

    @FXML
    private TableColumn<Clase, String> colHoraClase;

    @FXML
    private TableColumn<ReservaTabla, String> colHoraReserva;

    @FXML
    private TableColumn<Clase, String> colMonitorClase;

    @FXML
    private TableColumn<ReservaTabla, String> colMonitorReserva;

    @FXML
    private TableColumn<Clase, String> colSalaClase;

    @FXML
    private TableColumn<ReservaTabla, String> colSalaReserva;

    @FXML
    private TableView<Clase> tablaClases;

    @FXML
    private TableView<ReservaTabla> tablaReservas;

    private ObservableList<Clase> listaClases;
    private ObservableList<ReservaTabla> listaReservas;
    private ReservaDAO reservaDAO;
    private ClaseDAO claseDAO;
    private SocioDAO socioDAO;

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

        btnReservar.setOnAction(event -> {
            reservarClase();
        });
    }

    private void initGUI() {
        colClase.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colFechaClase.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHoraClase.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colSalaClase.setCellValueFactory(new PropertyValueFactory<>("sala"));
        colAforoMaximo.setCellValueFactory(new PropertyValueFactory<>("aforoMaximo"));
        colMonitorClase.setCellValueFactory(new PropertyValueFactory<>("monitor"));
        colEstadoClase.setCellValueFactory(new PropertyValueFactory<>("estado"));

        colClaseReserva.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFechaReserva.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHoraReserva.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colSalaReserva.setCellValueFactory(new PropertyValueFactory<>("sala"));
        colMonitorReserva.setCellValueFactory(new PropertyValueFactory<>("monitor"));
        colEstadoReserva.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarDatosReserva();
    }

    private void instances() {
        listaClases = FXCollections.observableArrayList();
        listaReservas = FXCollections.observableArrayList();
        claseDAO = new ClaseDAO();
        reservaDAO = new ReservaDAO();
        socioDAO = new SocioDAO();
    }

    private void cargarDatosReserva() {
        listaClases.clear();
        listaReservas.clear();

        try {
            listaClases.addAll(claseDAO.getAllClases());
            tablaClases.setItems(listaClases);

            int idUsuario = UsuarioLogueado.getUsuario().getIdUsuario();

            listaReservas.addAll(reservaDAO.getReservasByID(idUsuario));
            tablaReservas.setItems(listaReservas);

        }catch (SQLException e){
            System.out.println("Error al obtener las clases y reservas");
            System.out.println(e.getMessage());
        }
    }

    private void reservarClase() {
        try {
            Clase claseSeleccionada = tablaClases.getSelectionModel().getSelectedItem();

            if (claseSeleccionada == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Seleccione un clase");
                alert.setHeaderText(null);
                alert.setContentText("Debes seleccionar una clase");
                alert.showAndWait();
                return;
            }

            Usuario usuarioLogueado = UsuarioLogueado.getUsuario();

            if (usuarioLogueado == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Usuario no logueado");
                alert.setHeaderText(null);
                alert.setContentText("No hay un usuario logueado");
                alert.showAndWait();
                return;
            }

            Socio socio = socioDAO.getSocioById(usuarioLogueado.getIdUsuario());

            if (socio == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Socio no encontrado");
                alert.setHeaderText(null);
                alert.setContentText("No se ha encontrado al socio");
                alert.showAndWait();
                return;
            }

            Reserva reserva = new Reserva(
                    claseSeleccionada.getFecha(),
                    "confirmada",
                    "pendiente",
                    socio.getIdSocio(),
                    claseSeleccionada.getIdClase()
            );

            reservaDAO.insertReserva(reserva);
            cargarDatosReserva();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reserva Hecha");
            alert.setHeaderText(null);
            alert.setContentText("La reserva ha sido realizada correctamente");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Reserva duplicada");
            alert.setHeaderText(null);
            alert.setContentText("Ya has reservado esta clase");
            alert.showAndWait();
        }
    }
}

