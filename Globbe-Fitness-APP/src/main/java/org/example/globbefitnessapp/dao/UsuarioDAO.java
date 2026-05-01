package org.example.globbefitnessapp.dao;

import org.example.globbefitnessapp.database.DBConnection;
import org.example.globbefitnessapp.database.DBSchema;
import org.example.globbefitnessapp.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public Usuario login(String correoBuscar, String passBuscar) throws SQLException {
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?",
                DBSchema.TAB_USUARIO,
                DBSchema.USUARIO_CORREO, DBSchema.USUARIO_PASS);


        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, correoBuscar);
        preparedStatement.setString(2, passBuscar);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt(DBSchema.USUARIO_ID);
            String nombre = resultSet.getString(DBSchema.USUARIO_NOMBRE);
            String apellidos = resultSet.getString(DBSchema.USUARIO_APELLIDOS);
            String correo = resultSet.getString(DBSchema.USUARIO_CORREO);
            String password = resultSet.getString(DBSchema.USUARIO_PASS);
            String rol = resultSet.getString(DBSchema.USUARIO_ROL);

            return new Usuario(id,nombre,apellidos,correo,password,rol);
        }
        return null;
    }

    public List<Usuario> getAllUsuarios() throws SQLException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s",
                DBSchema.TAB_USUARIO);


        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            int id = resultSet.getInt(DBSchema.USUARIO_ID);
            String nombre = resultSet.getString(DBSchema.USUARIO_NOMBRE);
            String apellidos = resultSet.getString(DBSchema.USUARIO_APELLIDOS);
            String correo = resultSet.getString(DBSchema.USUARIO_CORREO);
            String password = resultSet.getString(DBSchema.USUARIO_PASS);
            String rol = resultSet.getString(DBSchema.USUARIO_ROL);

            listaUsuarios.add(new Usuario(id, nombre,apellidos,correo,password,rol));

        }
        return listaUsuarios;
    }

    public void updateUsuarioSocio(Usuario usuario) throws SQLException {
        connection = DBConnection.getConnection();

        String queryUsuario = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                DBSchema.TAB_USUARIO,
                DBSchema.USUARIO_NOMBRE,
                DBSchema.USUARIO_APELLIDOS,
                DBSchema.USUARIO_CORREO,
                DBSchema.USUARIO_PASS,
                DBSchema.USUARIO_ROL,
                DBSchema.USUARIO_ID
        );

        preparedStatement = connection.prepareStatement(queryUsuario);
        preparedStatement.setString(1, usuario.getNombre());
        preparedStatement.setString(2, usuario.getApellidos());
        preparedStatement.setString(3, usuario.getCorreo());
        preparedStatement.setString(4, usuario.getPassword());
        preparedStatement.setString(5, usuario.getRol());
        preparedStatement.setInt(6, usuario.getIdUsuario());
        preparedStatement.executeUpdate();

        String queryBuscarSocio = String.format(
                "SELECT * FROM %s WHERE %s = ?",
                DBSchema.TAB_SOCIO,
                DBSchema.SOCIO_ID_USUARIO
        );

        preparedStatement = connection.prepareStatement(queryBuscarSocio);
        preparedStatement.setInt(1, usuario.getIdUsuario());
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String querySocio = String.format(
                    "UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
                    DBSchema.TAB_SOCIO,
                    DBSchema.SOCIO_NOMBRE,
                    DBSchema.SOCIO_APELLIDOS,
                    DBSchema.SOCIO_EMAIL,
                    DBSchema.SOCIO_ID_USUARIO
            );

            preparedStatement = connection.prepareStatement(querySocio);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellidos());
            preparedStatement.setString(3, usuario.getCorreo());
            preparedStatement.setInt(4, usuario.getIdUsuario());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteUsuarioSocio(Usuario usuario) throws SQLException {
        connection = DBConnection.getConnection();

        String querySocio = String.format(
                "DELETE FROM %s WHERE %s = ?",
                DBSchema.TAB_SOCIO,
                DBSchema.SOCIO_ID_USUARIO
        );

        preparedStatement = connection.prepareStatement(querySocio);
        preparedStatement.setInt(1, usuario.getIdUsuario());
        preparedStatement.executeUpdate();

        String queryUsuario = String.format(
                "DELETE FROM %s WHERE %s = ?",
                DBSchema.TAB_USUARIO,
                DBSchema.USUARIO_ID
        );

        preparedStatement = connection.prepareStatement(queryUsuario);
        preparedStatement.setInt(1, usuario.getIdUsuario());
        preparedStatement.executeUpdate();
    }
}
