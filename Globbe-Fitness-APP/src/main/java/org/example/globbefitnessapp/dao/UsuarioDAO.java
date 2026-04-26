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

    public Usuario login(String correoBuscar, String passBuscar) {
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?",
                DBSchema.TAB_USUARIO,
                DBSchema.USUARIO_CORREO, DBSchema.USUARIO_PASS);

        try{
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


                return new Usuario(nombre,apellidos,correo,password,rol);
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void insertUsuario(Usuario usuario) throws SQLException{
        connection = DBConnection.getConnection();

        String query = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)",
                DBSchema.TAB_USUARIO,
                DBSchema.USUARIO_NOMBRE, DBSchema.USUARIO_APELLIDOS,
                DBSchema.USUARIO_CORREO, DBSchema.USUARIO_PASS, DBSchema.USUARIO_ROL);

        try{
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellidos());
            preparedStatement.setString(3, usuario.getCorreo());
            preparedStatement.setString(4, usuario.getPassword());
            preparedStatement.setString(5, usuario.getRol());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
    }

    public List<Usuario> getAllUsuarios(){
        List<Usuario> listaUsuarios = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s",
                DBSchema.TAB_USUARIO);

        try{
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(DBSchema.USUARIO_ID);
                String nombre = resultSet.getString(DBSchema.USUARIO_NOMBRE);
                String apellidos = resultSet.getString(DBSchema.USUARIO_APELLIDOS);
                String correo = resultSet.getString(DBSchema.USUARIO_CORREO);
                String password = resultSet.getString(DBSchema.USUARIO_PASS);
                String rol = resultSet.getString(DBSchema.USUARIO_ROL);

                listaUsuarios.add(new Usuario(nombre,apellidos,correo,password,rol));

            }
        }catch (SQLException e){
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
        return listaUsuarios;
    }
}
