package org.example.globbefitnessapp.dao;

import javafx.scene.control.Alert;
import org.example.globbefitnessapp.database.DBConnection;
import org.example.globbefitnessapp.database.DBSchema;
import org.example.globbefitnessapp.model.Reserva;
import org.example.globbefitnessapp.model.ReservaTabla;

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


        preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, reserva.getFechaReserva());
        preparedStatement.setString(2, reserva.getEstado());
        preparedStatement.setString(3, reserva.getAsistencia());
        preparedStatement.setInt(4, reserva.getIdSocio());
        preparedStatement.setInt(5, reserva.getIdClase());

        preparedStatement.executeUpdate();
    }

    public void deleteReserva(int id) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                DBSchema.TAB_RESERVA,
                DBSchema.RESERVA_ID
        );

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
    }

    public List<Reserva> getAllReservas() throws SQLException {
        List<Reserva> listaReservas = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s"
                ,DBSchema.TAB_RESERVA);


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

        return listaReservas;
    }

    public List<ReservaTabla> getReservasByID(int idUsuario) throws SQLException {
        List<ReservaTabla> lista = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format(
                "SELECT r.%s, c.%s, c.%s, c.%s, c.%s, c.%s, r.%s, r.%s FROM %s r INNER JOIN %s c ON r.%s = c.%s INNER JOIN %s s ON r.%s = s.%s WHERE s.%s = ?",
                DBSchema.RESERVA_ID,
                DBSchema.CLASE_NOMBRE,
                DBSchema.CLASE_FECHA,
                DBSchema.CLASE_HORA,
                DBSchema.CLASE_SALA,
                DBSchema.CLASE_MONITOR,
                DBSchema.RESERVA_ESTADO,
                DBSchema.RESERVA_ASISTENCIA,
                DBSchema.TAB_RESERVA,
                DBSchema.TAB_CLASE,
                DBSchema.RESERVA_ID_CLASE,
                DBSchema.CLASE_ID,
                DBSchema.TAB_SOCIO,
                DBSchema.RESERVA_ID_SOCIO,
                DBSchema.SOCIO_ID,
                DBSchema.SOCIO_ID_USUARIO
        );


        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idUsuario);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int idReserva = resultSet.getInt("id_reserva");
            String nombre = resultSet.getString("nombre");
            String fecha = resultSet.getString("fecha");
            String hora = resultSet.getString("hora");
            String sala = resultSet.getString("sala");
            String monitor = resultSet.getString("monitor");
            String estado = resultSet.getString("estado");
            String asistencia = resultSet.getString("asistencia");

            lista.add(new ReservaTabla(idReserva, nombre, fecha, hora, sala, monitor, estado, asistencia));
        }

        return lista;
    }

    public void updateReserva(Reserva reserva) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                DBSchema.TAB_RESERVA,
                DBSchema.RESERVA_FECHA,
                DBSchema.RESERVA_ESTADO,
                DBSchema.RESERVA_ASISTENCIA,
                DBSchema.RESERVA_ID_SOCIO,
                DBSchema.RESERVA_ID_CLASE,
                DBSchema.RESERVA_ID
        );

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, reserva.getFechaReserva());
        preparedStatement.setString(2, reserva.getEstado());
        preparedStatement.setString(3, reserva.getAsistencia());
        preparedStatement.setInt(4, reserva.getIdSocio());
        preparedStatement.setInt(5, reserva.getIdClase());
        preparedStatement.setInt(6, reserva.getIdReserva());

        preparedStatement.executeUpdate();
    }
}
