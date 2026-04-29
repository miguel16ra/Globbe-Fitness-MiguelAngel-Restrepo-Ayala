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

    public void insertSocioUsuario(Socio socio) throws SQLException {
        connection = DBConnection.getConnection();

        String querySocio = String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?,?,?)",
                DBSchema.TAB_SOCIO, DBSchema.SOCIO_NOMBRE, DBSchema.SOCIO_APELLIDOS,
                DBSchema.SOCIO_DNI, DBSchema.SOCIO_EMAIL, DBSchema.SOCIO_TELEFONO, DBSchema.SOCIO_FECHA_ALTA,
                DBSchema.SOCIO_ESTADO, DBSchema.SOCIO_ID_PLAN, DBSchema.SOCIO_ID_USUARIO);

        String queryUsuario = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                DBSchema.TAB_USUARIO,
                DBSchema.USUARIO_NOMBRE, DBSchema.USUARIO_APELLIDOS, DBSchema.USUARIO_CORREO, DBSchema.USUARIO_PASS,
                DBSchema.USUARIO_ROL);


        preparedStatement = connection.prepareStatement(queryUsuario, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, socio.getNombre());
        preparedStatement.setString(2, socio.getApellidos());
        preparedStatement.setString(3, socio.getEmail());
        preparedStatement.setString(4, socio.getPassword());
        preparedStatement.setString(5, "USER");

        preparedStatement.executeUpdate();

        resultSet = preparedStatement.getGeneratedKeys();
        int idUsuarioGenerated = 0;

        if(resultSet.next()){
            idUsuarioGenerated = resultSet.getInt(1);
        }

        preparedStatement = connection.prepareStatement(querySocio);

        preparedStatement.setString(1, socio.getNombre());
        preparedStatement.setString(2, socio.getApellidos());
        preparedStatement.setString(3, socio.getDni());
        preparedStatement.setString(4, socio.getEmail());
        preparedStatement.setString(5, socio.getTelefono());
        preparedStatement.setString(6, socio.getFechaAlta());
        preparedStatement.setString(7, socio.getEstado());
        preparedStatement.setInt(8, socio.getIdPlan());
        preparedStatement.setInt(9, idUsuarioGenerated);

        preparedStatement.executeUpdate();

    }

    public void updateSocio(Socio socio) throws SQLException{}

    public void deleteSocio(int id) throws SQLException{}

    public List<Socio> getAllSocios() throws SQLException {
        List<Socio> listaSocios = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s",
                DBSchema.TAB_SOCIO);


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
            String estado = resultSet.getString(DBSchema.SOCIO_ESTADO);
            int idPlan = resultSet.getInt(DBSchema.SOCIO_ID_PLAN);

            listaSocios.add(new Socio(id, nombre, apellidos, dni, email, telefono, fechaAlta, estado, idPlan));
        }

        return listaSocios;
    }

    public Socio getSocioById(int idBuscar) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s WHERE %s = ?",
                DBSchema.TAB_SOCIO,
                DBSchema.SOCIO_ID_USUARIO);


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
            String estado = resultSet.getString(DBSchema.SOCIO_ESTADO);
            int idPlan = resultSet.getInt(DBSchema.SOCIO_ID_PLAN);
            int idUsuarioSocio = resultSet.getInt(DBSchema.SOCIO_ID_USUARIO);

            Socio socio = new Socio(id,nombre, apellidos, dni, email, telefono, fechaAlta, estado, idPlan);
            socio.setIdUsuario(idUsuarioSocio);
            return socio;
        }

        return null;
    }
}
