package org.example.globbefitnessapp.dao;

import org.example.globbefitnessapp.database.DBConnection;
import org.example.globbefitnessapp.database.DBSchema;
import org.example.globbefitnessapp.model.DetalleVenta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void insertDetalleVenta(DetalleVenta detalleVenta) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)",
                DBSchema.TAB_DETALLE_VENTA,
                DBSchema.DETALLE_VENTA_CANTIDAD, DBSchema.DETALLE_VENTA_PUNITARIO, DBSchema.DETALLE_VENTA_TOTAL,
                DBSchema.DETALLE_VENTA_ID_VENTA, DBSchema.DETALLE_VENTA_ID_PRODUCTO);

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, detalleVenta.getCantidad());
        preparedStatement.setDouble(2, detalleVenta.getPrecioUnitario());
        preparedStatement.setDouble(3, detalleVenta.getTotal());
        preparedStatement.setInt(4, detalleVenta.getIdVenta());
        preparedStatement.setInt(5, detalleVenta.getIdProducto());

        preparedStatement.executeUpdate();
    }

    public List<DetalleVenta> getAllDetalles(){
        List<DetalleVenta> listaDetalleVenta = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query =  String.format("SELECT * FROM %s",
                DBSchema.TAB_DETALLE_VENTA);

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt(DBSchema.DETALLE_VENTA_ID);
                int cantidad = resultSet.getInt(DBSchema.DETALLE_VENTA_CANTIDAD);
                double precioUnitario = resultSet.getDouble(DBSchema.DETALLE_VENTA_PUNITARIO);
                double total = resultSet.getDouble(DBSchema.DETALLE_VENTA_TOTAL);
                int idVenta = resultSet.getInt(DBSchema.DETALLE_VENTA_ID_VENTA);
                int idProducto = resultSet.getInt(DBSchema.DETALLE_VENTA_ID_PRODUCTO);

                listaDetalleVenta.add(new DetalleVenta(id, cantidad,precioUnitario,total,idVenta,idProducto));
            }
        }catch (SQLException e){
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
        return listaDetalleVenta;
    }
}
