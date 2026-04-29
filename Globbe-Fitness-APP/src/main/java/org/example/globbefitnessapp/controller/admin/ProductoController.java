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
import org.example.globbefitnessapp.dao.ProductoDAO;
import org.example.globbefitnessapp.model.Producto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductoController implements Initializable {

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
    private ComboBox<String> cmbActivo;

    @FXML
    private ComboBox<Integer> cmbIdCategoria;

    @FXML
    private ComboBox<Integer> cmbIdOferta;

    @FXML
    private TableColumn<Producto, String> colActivo;

    @FXML
    private TableColumn<Producto, String> colDescripcion;

    @FXML
    private TableColumn<Producto, Integer> colId;

    @FXML
    private TableColumn<Producto, Integer> colIdCategoria;

    @FXML
    private TableColumn<Producto, Integer> colIdOferta;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colStock;

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    private ObservableList<Producto> listaProductos;
    private FilteredList<Producto> listaFiltrada;
    private ProductoDAO productoDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        actions();
        initGUI();
    }

    private void initGUI() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        colIdOferta.setCellValueFactory(new PropertyValueFactory<>("idOferta"));
        cmbActivo.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        cmbIdCategoria.setItems((FXCollections.observableArrayList(1,2,3)));
        cmbIdOferta.setItems((FXCollections.observableArrayList(1,2)));
        cargarProductos();

        tablaProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtNombre.setText(newValue.getNombre());
                txtDescripcion.setText(newValue.getDescripcion());
                txtStock.setText(String.valueOf(newValue.getStock()));
                txtPrecio.setText(String.valueOf(newValue.getPrecio()));
                cmbActivo.setValue(newValue.getActivo());
                cmbIdCategoria.setValue(newValue.getIdCategoria());
                cmbIdOferta.setValue(newValue.getIdOferta());
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
                listaFiltrada.setPredicate(producto -> producto.getNombre().toLowerCase().contains(newValue.toLowerCase()));
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

            try{
                Producto producto = new Producto(
                        txtNombre.getText(),
                        txtDescripcion.getText(),
                        Double.parseDouble(txtPrecio.getText()),
                        Integer.parseInt(txtStock.getText()),
                        cmbActivo.getValue(),
                        cmbIdCategoria.getValue(),
                        cmbIdOferta.getValue()
                );

                productoDAO.insertProducto(producto);

                cargarProductos();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Añadir Producto");
                alert.setHeaderText(null);
                alert.setContentText("Producto agregado exitosamente");
                alert.showAndWait();

                limpiarCampos();

            }catch (SQLException e){
                System.out.println("Ha ocurrido un error");
                System.out.println(e.getMessage());
            }
        });
    }

    private boolean camposVacios() {
        return txtNombre.getText().isEmpty()
                || txtDescripcion.getText().isEmpty()
                || txtPrecio.getText().isEmpty()
                || txtStock.getText().isEmpty()
                || cmbActivo.getValue() == null
                || cmbIdCategoria.getValue() == null;
    }

    private void instances() {
        listaProductos = FXCollections.observableArrayList();
        listaFiltrada = new FilteredList<>(listaProductos);
        productoDAO = new ProductoDAO();
    }

    private void cargarProductos() {
        listaProductos.clear();

        try {
            listaProductos.addAll(productoDAO.getAllProductos());
            tablaProductos.setItems(listaFiltrada);

        } catch (Exception e) {
            System.out.println("Error al cargar productos");
            System.out.println(e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtBuscar.clear();
        cmbActivo.setValue(null);
        cmbIdCategoria.setValue(null);
        cmbIdOferta.setValue(null);
        tablaProductos.getSelectionModel().clearSelection();
    }
}

