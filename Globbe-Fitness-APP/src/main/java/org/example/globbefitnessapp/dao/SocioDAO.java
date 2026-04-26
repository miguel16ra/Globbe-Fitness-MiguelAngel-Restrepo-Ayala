package org.example.globbefitnessapp.dao;

import org.example.globbefitnessapp.database.DBConnection;
import org.example.globbefitnessapp.database.DBSchema;
import org.example.globbefitnessapp.model.Socio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocioDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void insertSocio(Socio socio) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?,?)",
                DBSchema.TAB_SOCIO, DBSchema.SOCIO_NOMBRE, DBSchema.SOCIO_APELLIDOS,
                DBSchema.SOCIO_DNI, DBSchema.SOCIO_EMAIL, DBSchema.SOCIO_TELEFONO, DBSchema.SOCIO_FECHA_ALTA,
                DBSchema.SOCIO_ESTADO, DBSchema.SOCIO_ID_PLAN);

        try{
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, socio.getNombre());
            preparedStatement.setString(2, socio.getApellidos());
            preparedStatement.setString(3, socio.getDni());
            preparedStatement.setString(4, socio.getEmail());
            preparedStatement.setString(5, socio.getTelefono());
            preparedStatement.setString(6, socio.getFechaAlta());
            preparedStatement.setBoolean(7, socio.isEstado());
            preparedStatement.setInt(8, socio.getIdPlan());

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
    }

    public void updateSocio(Socio socio) throws SQLException{}

    public void deleteSocio(int id) throws SQLException{}

    public List<Socio> getAllSocios(){
        List<Socio> listaSocios = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s",
                DBSchema.TAB_SOCIO);

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(DBSchema.SOCIO_ID);
                String nombre = resultSet.getString(DBSchema.SOCIO_NOMBRE);
                String apellidos = resultSet.getString(DBSchema.SOCIO_APELLIDOS);
                String dni = resultSet.getString(DBSchema.SOCIO_DNI);
                String email = resultSet.getString(DBSchema.SOCIO_EMAIL);
                String telefono = resultSet.getString(DBSchema.SOCIO_TELEFONO);
                String fechaAlta = resultSet.getString(DBSchema.SOCIO_FECHA_ALTA);
                boolean estado = resultSet.getBoolean(DBSchema.SOCIO_ESTADO);
                int idPlan = resultSet.getInt(DBSchema.SOCIO_ID_PLAN);

                listaSocios.add(new Socio(nombre, apellidos, dni,  email, telefono, fechaAlta, estado, idPlan));
            }
        }catch (SQLException e){
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
        return listaSocios;
    }

    public Socio getSocioById(int idBuscar){
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s WHERE %s = ?",
                DBSchema.TAB_SOCIO,
                DBSchema.SOCIO_ID);

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idBuscar);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(DBSchema.SOCIO_ID);
                String nombre = resultSet.getString(DBSchema.SOCIO_NOMBRE);
                String apellidos = resultSet.getString(DBSchema.SOCIO_APELLIDOS);
                String dni = resultSet.getString(DBSchema.SOCIO_DNI);
                String email = resultSet.getString(DBSchema.SOCIO_EMAIL);
                String telefono = resultSet.getString(DBSchema.SOCIO_TELEFONO);
                String fechaAlta = resultSet.getString(DBSchema.SOCIO_FECHA_ALTA);
                boolean estado = resultSet.getBoolean(DBSchema.SOCIO_ESTADO);
                int idPlan = resultSet.getInt(DBSchema.SOCIO_ID_PLAN);

                return new Socio(nombre, apellidos, dni, email, telefono, fechaAlta, estado, idPlan);
            }
        }catch (SQLException e){
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
