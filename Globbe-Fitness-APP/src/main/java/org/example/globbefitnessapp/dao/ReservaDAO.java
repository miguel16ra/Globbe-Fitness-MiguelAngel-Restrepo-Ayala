package org.example.globbefitnessapp.dao;

import org.example.globbefitnessapp.database.DBConnection;
import org.example.globbefitnessapp.database.DBSchema;
import org.example.globbefitnessapp.model.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void insertReserva(Reserva reserva) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)",
                DBSchema.TAB_RESERVA,
                DBSchema.RESERVA_FECHA, DBSchema.RESERVA_ESTADO, DBSchema.RESERVA_ASISTENCIA,
                DBSchema.RESERVA_ID_SOCIO,  DBSchema.RESERVA_ID_CLASE);

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, reserva.getFechaReserva());
            preparedStatement.setString(2, reserva.getEstado());
            preparedStatement.setString(3, reserva.getAsistencia());
            preparedStatement.setInt(4, reserva.getIdSocio());
            preparedStatement.setInt(5, reserva.getIdClase());

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
    }

    public void deleteReserva(int id) throws SQLException{}

    public List<Reserva> getAllReservas(){
        List<Reserva> listaReservas = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s"
                ,DBSchema.TAB_RESERVA);

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(DBSchema.RESERVA_ID);
                String fechaReserva = resultSet.getString(DBSchema.RESERVA_FECHA);
                String estado = resultSet.getString(DBSchema.RESERVA_ESTADO);
                String asistencia = resultSet.getString(DBSchema.RESERVA_ASISTENCIA);
                int idSocio = resultSet.getInt(DBSchema.RESERVA_ID_SOCIO);
                int idClase = resultSet.getInt(DBSchema.RESERVA_ID_CLASE);

                listaReservas.add(new Reserva(id, fechaReserva, estado, asistencia, idSocio, idClase));
            }
        }catch (SQLException e){
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
        return listaReservas;
    }
}
