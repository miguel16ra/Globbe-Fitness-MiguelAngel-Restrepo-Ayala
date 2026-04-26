package org.example.globbefitnessapp.dao;

import org.example.globbefitnessapp.database.DBConnection;
import org.example.globbefitnessapp.database.DBSchema;
import org.example.globbefitnessapp.model.Clase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaseDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void insertClase(Clase clase) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?,?)",
                DBSchema.TAB_CLASE,
                DBSchema.CLASE_NOMBRE, DBSchema.CLASE_DESCRIPCION, DBSchema.CLASE_FECHA, DBSchema.CLASE_HORA,
                DBSchema.CLASE_SALA, DBSchema.CLASE_AFORO, DBSchema.CLASE_MONITOR, DBSchema.CLASE_ESTADO);

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, clase.getNombre());
        preparedStatement.setString(2, clase.getDescripcion());
        preparedStatement.setString(3, clase.getFecha());
        preparedStatement.setString(4, clase.getHora());
        preparedStatement.setString(5, clase.getSala());
        preparedStatement.setInt(6, clase.getAforoMaximo());
        preparedStatement.setString(7,clase.getMonitor());
        preparedStatement.setString(8, clase.getEstado());

        preparedStatement.executeUpdate();
    }

    public List<Clase> getAllClases(){
        List<Clase> listaClases = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s",DBSchema.TAB_CLASE);

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt(DBSchema.CLASE_ID);
                String nombre = resultSet.getString(DBSchema.CLASE_NOMBRE);
                String descripcion = resultSet.getString(DBSchema.CLASE_DESCRIPCION);
                String fecha = resultSet.getString(DBSchema.CLASE_FECHA);
                String hora = resultSet.getString(DBSchema.CLASE_HORA);
                String sala = resultSet.getString(DBSchema.CLASE_SALA);
                int aforoMaximo = resultSet.getInt(DBSchema.CLASE_AFORO);
                String monitor = resultSet.getString(DBSchema.CLASE_MONITOR);
                String estado = resultSet.getString(DBSchema.CLASE_ESTADO);
                listaClases.add(new Clase(nombre, descripcion, fecha, hora, sala, aforoMaximo, monitor, estado));
            }
        }catch (SQLException e){
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
        return listaClases;
    }

    public Clase getClaseById(int idBuscar){
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s WHERE %s = ?",
                DBSchema.TAB_CLASE,
                DBSchema.CLASE_ID);

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idBuscar);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt(DBSchema.CLASE_ID);
                String nombre = resultSet.getString(DBSchema.CLASE_NOMBRE);
                String descripcion = resultSet.getString(DBSchema.CLASE_DESCRIPCION);
                String fecha = resultSet.getString(DBSchema.CLASE_FECHA);
                String hora = resultSet.getString(DBSchema.CLASE_HORA);
                String sala = resultSet.getString(DBSchema.CLASE_SALA);
                int aforoMaximo = resultSet.getInt(DBSchema.CLASE_AFORO);
                String monitor = resultSet.getString(DBSchema.CLASE_MONITOR);
                String estado = resultSet.getString(DBSchema.CLASE_ESTADO);

                return new Clase(nombre, descripcion, fecha, hora, sala, aforoMaximo, monitor, estado);
            }
        }catch (SQLException e){
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
