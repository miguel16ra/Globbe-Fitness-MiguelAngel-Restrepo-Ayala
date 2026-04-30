package org.example.globbefitnessapp.controller.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.globbefitnessapp.HelloApplication;
import org.example.globbefitnessapp.dao.ProductoDAO;
import org.example.globbefitnessapp.dao.VentaDAO;
import org.example.globbefitnessapp.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TiendaController implements Initializable {

    @FXML
    private Button btnAnadir;

    @FXML
    private Button btnFinalizarCompra;

    @FXML
    private Button btnQuitar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> cbMetodoPago;

    @FXML
    private TableColumn<Producto, String> colActivoProducto;

    @FXML
    private TableColumn<Carrito, Integer> colCantidadCarrito;

    @FXML
    private TableColumn<Producto, String> colDescripcionProducto;

    @FXML
    private TableColumn<Venta, String> colFechaCompra;

    @FXML
    private TableColumn<Venta, String> colMetodoPagoCompra;

    @FXML
    private TableColumn<Carrito, String> colNombreCarrito;

    @FXML
    private TableColumn<Producto, String> colNombreProducto;

    @FXML
    private TableColumn<Carrito, Double> colPrecioCarrito;

    @FXML
    private TableColumn<Producto, Double> colPrecioProducto;

    @FXML
    private TableColumn<Producto, Integer> colStockProducto;

    @FXML
    private TableColumn<Carrito, Double> colSubtotalCarrito;

    @FXML
    private TableColumn<Venta, Double> colTotalCompra;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<Carrito> tablaCarrito;

    @FXML
    private TableView<Venta> tablaCompras;

    @FXML
    private TableView<Producto> tablaProductos;

    private ObservableList<Producto> listaProductos;
    private ObservableList<Carrito> listaCarrito;
    private ObservableList<Venta> listaCompras;
    private ProductoDAO productoDAO;
    private VentaDAO ventaDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        actions();
        initGUI();
    }

    private void initGUI() {
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcionProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecioProducto.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStockProducto.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colActivoProducto.setCellValueFactory(new PropertyValueFactory<>("activo"));

        colNombreCarrito.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecioCarrito.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidadCarrito.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colSubtotalCarrito.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        colTotalCompra.setCellValueFactory(new PropertyValueFactory<>("total"));
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        colMetodoPagoCompra.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));

        cbMetodoPago.setItems(FXCollections.observableArrayList("Efectivo", "Tarjeta"));
        lblTotal.setText("0.00€");

        cargarProductos();


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
                System.out.println(e.getMessage());
            }
        });

        btnAnadir.setOnAction(event -> {
            Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

            if (productoSeleccionado == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Producto");
                alert.setHeaderText(null);
                alert.setContentText("Seleccione el producto que desea añaaadir");
                alert.showAndWait();
                return;
            }

            if (productoSeleccionado.getActivo().equalsIgnoreCase("Inactivo")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Producto");
                alert.setHeaderText(null);
                alert.setContentText("El producto que ha seleccionado no se encuentra disponible");
                alert.showAndWait();
                return;
            }

            if (productoSeleccionado.getStock() < 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Producto");
                alert.setHeaderText(null);
                alert.setContentText("El producto que ha seleccinado se encuentra fuera de stock");
                alert.showAndWait();
                return;
            }

            for (Carrito item : listaCarrito) {
                if (item.getIdProducto() == productoSeleccionado.getIdProducto()) {
                    if (item.getCantidad() < productoSeleccionado.getStock()) {
                        item.setCantidad(item.getCantidad() + 1);
                        tablaCarrito.refresh();
                        actualizarTotal();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Stock");
                        alert.setHeaderText(null);
                        alert.setContentText("No puedes añadir más unidades de este producto");
                        alert.showAndWait();
                    }
                    return;
                }
            }

            listaCarrito.add(new Carrito(
                    productoSeleccionado.getIdProducto(),
                    productoSeleccionado.getNombre(),
                    productoSeleccionado.getPrecio(),
                    1
            ));


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Producto");
            alert.setHeaderText(null);
            alert.setContentText("Producto agregado al carrito");
            alert.showAndWait();
            actualizarTotal();
        });

        btnQuitar.setOnAction(event -> {
            Carrito productoSeleccionado = tablaCarrito.getSelectionModel().getSelectedItem();

            if (productoSeleccionado == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Producto");
                alert.setContentText("Seleccione el producto que desea quitar");
                alert.showAndWait();
                return;
            }

            if (productoSeleccionado.getCantidad() > 1) {
                productoSeleccionado.setCantidad(productoSeleccionado.getCantidad() - 1);
                tablaCarrito.refresh();
            } else {
                listaCarrito.remove(productoSeleccionado);
            }

            actualizarTotal();
        });

        btnFinalizarCompra.setOnAction(event -> {
            if (listaCarrito.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Compra");
                alert.setHeaderText(null);
                alert.setContentText("El carrito está vacío");
                alert.showAndWait();
                return;
            }

            if (cbMetodoPago.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Método de pago");
                alert.setHeaderText(null);
                alert.setContentText("Seleccione un método de pago");
                alert.showAndWait();
                return;
            }

            if (UsuarioLogueado.getUsuario() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sesión");
                alert.setHeaderText(null);
                alert.setContentText("No hay ningún usuario logueado");
                alert.showAndWait();
                return;
            }

            int idSocio = UsuarioLogueado.getUsuario().getIdUsuario();
            String metodoPago = cbMetodoPago.getValue();
            String fechaVenta = java.time.LocalDate.now().toString();

            double total = 0.0;
            for (Carrito item : listaCarrito) {
                total += item.getSubtotal();
            }

            Venta venta = new Venta(fechaVenta, metodoPago, total, idSocio);;

            try {
                ventaDAO.insertVenta(venta);

                for (Carrito item : listaCarrito) {
                    for (Producto producto : listaProductos) {
                        if (producto.getIdProducto() == item.getIdProducto()) {
                            int nuevoStock = producto.getStock() - item.getCantidad();
                            productoDAO.updateStock(producto.getIdProducto(), nuevoStock);
                            break;
                        }
                    }
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Compra");
                alert.setHeaderText(null);
                alert.setContentText("Compra realizada correctamente");
                alert.showAndWait();

                cbMetodoPago.setValue(null);
                cargarProductos();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo finalizar la compra");
                alert.showAndWait();

                System.out.println(e.getMessage());
            }
        });
    }

    private void instances() {
        listaProductos = FXCollections.observableArrayList();
        listaCarrito = FXCollections.observableArrayList();
        listaCompras = FXCollections.observableArrayList();
        productoDAO = new ProductoDAO();
        ventaDAO = new VentaDAO();

    }

    private void actualizarTotal() {
        double total = 0;

        for (Carrito item : listaCarrito) {
            total += item.getSubtotal();
        }
        lblTotal.setText(String.format("%.2f", total));
    }

    private void cargarProductos() {
        listaProductos.clear();
        listaCarrito.clear();
        listaCompras.clear();
        try {
            listaProductos.addAll(productoDAO.getAllProductos());

            if (UsuarioLogueado.getUsuario() != null) {
                listaCompras.addAll(ventaDAO.getVentasByID(UsuarioLogueado.getUsuario().getIdUsuario()));
            }

            tablaProductos.setItems(listaProductos);
            tablaCarrito.setItems(listaCarrito);
            tablaCompras.setItems(listaCompras);

            actualizarTotal();

        } catch (SQLException e) {
            System.out.println("error");
            System.out.println(e.getMessage());
        }
    }
}