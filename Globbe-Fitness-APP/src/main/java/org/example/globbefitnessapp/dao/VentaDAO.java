package org.example.globbefitnessapp.dao;

import org.example.globbefitnessapp.database.DBConnection;
import org.example.globbefitnessapp.database.DBSchema;
import org.example.globbefitnessapp.model.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void insertVenta(Venta venta) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES (?,?,?,?)",
                DBSchema.TAB_VENTA,
                DBSchema.VENTA_FECHA, DBSchema.VENTA_METODO_PAGO, DBSchema.VENTA_TOTAL,
                DBSchema.VENTA_ID_SOCIO);


        preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, venta.getFechaVenta());
        preparedStatement.setString(2, venta.getMetodoPago());
        preparedStatement.setDouble(3, venta.getTotal());
        preparedStatement.setInt(4, venta.getIdSocio());

        preparedStatement.executeUpdate();
    }

    public List<Venta> getAllVentas() throws SQLException {
        List<Venta> listaVentas = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s",
                DBSchema.TAB_VENTA);


        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            int id = resultSet.getInt(DBSchema.VENTA_ID);
            String fecha = resultSet.getString(DBSchema.VENTA_FECHA);
            String metodoPago = resultSet.getString(DBSchema.VENTA_METODO_PAGO);
            double total = resultSet.getDouble(DBSchema.VENTA_TOTAL);
            int idSocio = resultSet.getInt(DBSchema.VENTA_ID_SOCIO);

            listaVentas.add(new Venta(id,fecha,metodoPago,total,idSocio));
        }
        return listaVentas;
    }
}
