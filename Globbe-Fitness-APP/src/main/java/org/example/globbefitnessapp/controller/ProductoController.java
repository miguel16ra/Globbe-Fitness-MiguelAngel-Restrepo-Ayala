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
import org.example.globbefitnessapp.model.Producto;
import org.example.globbefitnessapp.model.Socio;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

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
        cargarSocios();

        tablaProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtNombre.setText(newValue.getNombre());
            txtDescripcion.setText(newValue.getDescripcion());
            txtStock.setText(String.valueOf(newValue.getStock()));
            txtPrecio.setText(String.valueOf(newValue.getPrecio()));
            cmbActivo.setValue(newValue.getActivo());
            cmbIdCategoria.setValue(newValue.getIdCategoria());
            cmbIdOferta.setValue(newValue.getIdOferta());

        });
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
            connection = DBConnection.getConnection();

            if (camposVacios()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Campos Vacios");
                alert.setHeaderText(null);
                alert.setContentText("Por favor rellene todos los campos");
                alert.showAndWait();
                return;
            }

            String query = String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?)",
                    DBSchema.TAB_PRODUCTO,
                    DBSchema.PRODUCTO_NOMBRE, DBSchema.PRODUCTO_DESCRIPCION, DBSchema.PRODUCTO_PRECIO, DBSchema.PRODUCTO_STOCK,
                    DBSchema.PRODUCTO_ACTIVO, DBSchema.PRODUCTO_ID_CATEGORIA, DBSchema.PRODUCTO_ID_OFERTA);

            try{
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, txtNombre.getText());
                preparedStatement.setString(2, txtDescripcion.getText());
                preparedStatement.setDouble(3, Double.parseDouble(txtPrecio.getText()));
                preparedStatement.setInt(4, Integer.parseInt(txtStock.getText()));
                preparedStatement.setString(5, cmbActivo.getValue());
                preparedStatement.setInt(6, cmbIdCategoria.getValue());
                preparedStatement.setInt(7, cmbIdOferta.getValue());

                preparedStatement.executeUpdate();

                cargarSocios();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Añadir Socio");
                alert.setHeaderText(null);
                alert.setContentText("Socio agregado exitosamente");
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
                || cmbIdCategoria.getValue() == null
                || cmbIdOferta.getValue() == null;
    }

    private void instances() {
        listaProductos = FXCollections.observableArrayList();
        listaFiltrada = new FilteredList<>(listaProductos);
    }

    private void cargarSocios() {
        connection = DBConnection.getConnection();
        listaProductos.clear();

        String query = String.format("SELECT * FROM %s",
                DBSchema.TAB_PRODUCTO);

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Producto producto = new Producto(
                        resultSet.getInt(DBSchema.PRODUCTO_ID),
                        resultSet.getString(DBSchema.PRODUCTO_NOMBRE),
                        resultSet.getString(DBSchema.PRODUCTO_DESCRIPCION),
                        resultSet.getDouble(DBSchema.PRODUCTO_PRECIO),
                        resultSet.getInt(DBSchema.PRODUCTO_STOCK),
                        resultSet.getString(DBSchema.PRODUCTO_ACTIVO),
                        resultSet.getInt(DBSchema.PRODUCTO_ID_CATEGORIA),
                        resultSet.getInt(DBSchema.PRODUCTO_ID_OFERTA)
                );
                listaProductos.add(producto);
            }

            tablaProductos.setItems(listaFiltrada);

        }catch (SQLException e){
            System.out.println("Error al cargar socios");
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

