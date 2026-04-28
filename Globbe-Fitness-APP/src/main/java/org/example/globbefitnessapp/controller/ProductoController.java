package org.example.globbefitnessapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.globbefitnessapp.database.DBConnection;
import org.example.globbefitnessapp.database.DBSchema;
import org.example.globbefitnessapp.model.Producto;
import org.example.globbefitnessapp.model.Socio;

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
        cargarSocios();



    }

    private void actions() {

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
}

