package org.example.globbefitnessapp.dao;

import org.example.globbefitnessapp.database.DBConnection;
import org.example.globbefitnessapp.database.DBSchema;
import org.example.globbefitnessapp.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void insertProducto(Producto producto) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?)",
                DBSchema.TAB_PRODUCTO,
                DBSchema.PRODUCTO_NOMBRE, DBSchema.PRODUCTO_DESCRIPCION, DBSchema.PRODUCTO_PRECIO,
                DBSchema.PRODUCTO_STOCK, DBSchema.PRODUCTO_ACTIVO,
                DBSchema.PRODUCTO_ID_CATEGORIA, DBSchema.PRODUCTO_ID_OFERTA);

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, producto.getNombre());
        preparedStatement.setString(2, producto.getDescripcion());
        preparedStatement.setDouble(3, producto.getPrecio());
        preparedStatement.setInt(4, producto.getStock());
        preparedStatement.setString(5, producto.getActivo());
        preparedStatement.setInt(6, producto.getIdCategoria());
        if (producto.getIdOferta() == null) {
            preparedStatement.setNull(7, java.sql.Types.INTEGER);
        } else {
            preparedStatement.setInt(7, producto.getIdOferta());
        }

        preparedStatement.executeUpdate();
    }

    public void updateProducto(Producto producto) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                DBSchema.TAB_PRODUCTO,
                DBSchema.PRODUCTO_NOMBRE,
                DBSchema.PRODUCTO_DESCRIPCION,
                DBSchema.PRODUCTO_PRECIO,
                DBSchema.PRODUCTO_STOCK,
                DBSchema.PRODUCTO_ACTIVO,
                DBSchema.PRODUCTO_ID_CATEGORIA,
                DBSchema.PRODUCTO_ID_OFERTA,
                DBSchema.PRODUCTO_ID
        );

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, producto.getNombre());
        preparedStatement.setString(2, producto.getDescripcion());
        preparedStatement.setDouble(3, producto.getPrecio());
        preparedStatement.setInt(4, producto.getStock());
        preparedStatement.setString(5, producto.getActivo());
        preparedStatement.setInt(6, producto.getIdCategoria());

        if (producto.getIdOferta() == null) {
            preparedStatement.setNull(7, Types.INTEGER);
        } else {
            preparedStatement.setInt(7, producto.getIdOferta());
        }

        preparedStatement.setInt(8, producto.getIdProducto());

        preparedStatement.executeUpdate();
    }

    public void updateStock(int idProducto, int nuevoStock) throws SQLException{
        connection = DBConnection.getConnection();

        String query = String.format("UPDATE %s SET %s = ? WHERE %s = ?",
                DBSchema.TAB_PRODUCTO,
                DBSchema.PRODUCTO_STOCK,
                DBSchema.PRODUCTO_ID);

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, nuevoStock);
        preparedStatement.setInt(2, idProducto);

        preparedStatement.executeUpdate();
    }

    public void deleteProducto(int id) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                DBSchema.TAB_PRODUCTO,
                DBSchema.PRODUCTO_ID
        );

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
    }

    public List<Producto> getAllProductos() throws SQLException {
        List<Producto> listaProductos = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s",
                DBSchema.TAB_PRODUCTO);


        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            int id = resultSet.getInt(DBSchema.PRODUCTO_ID);
            String nombre = resultSet.getString(DBSchema.PRODUCTO_NOMBRE);
            String descripcion = resultSet.getString(DBSchema.PRODUCTO_DESCRIPCION);
            double precio = resultSet.getDouble(DBSchema.PRODUCTO_PRECIO);
            int stock = resultSet.getInt(DBSchema.PRODUCTO_STOCK);
            String activo = resultSet.getString(DBSchema.PRODUCTO_ACTIVO);
            int idCategoria = resultSet.getInt(DBSchema.PRODUCTO_ID_CATEGORIA);
            Integer idOferta = (Integer) resultSet.getObject(DBSchema.PRODUCTO_ID_OFERTA);

            listaProductos.add(new Producto(id, nombre, descripcion, precio, stock, activo, idCategoria, idOferta));
        }

        return listaProductos;
    }
}
