package org.example.globbefitnessapp.dao;

import org.example.globbefitnessapp.database.DBConnection;
import org.example.globbefitnessapp.database.DBSchema;
import org.example.globbefitnessapp.model.Plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public List<Plan> getAllPlanes(){
        List<Plan> listaPlanes = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s",
                DBSchema.TAB_PLAN);

        try{
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(DBSchema.PLAN_ID);
                String nombre = resultSet.getString(DBSchema.PLAN_NOMBRE);
                String descripcion = resultSet.getString(DBSchema.PLAN_DESCRIPCION);
                double precio = resultSet.getDouble(DBSchema.PLAN_PRECIO);
                int duracion = resultSet.getInt(DBSchema.PLAN_DURACION);
                Boolean activo = resultSet.getBoolean(DBSchema.PLAN_ACTIVO);

                listaPlanes.add(new Plan(id,nombre,descripcion,precio,duracion,activo));
            }

        }catch (SQLException e){
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
        return listaPlanes;
    }

    public Plan getPlanById(int idBuscar){
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s WHERE %s = ?",
                DBSchema.TAB_PLAN,
                DBSchema.PLAN_ID);

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idBuscar);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(DBSchema.PLAN_ID);
                String nombre = resultSet.getString(DBSchema.PLAN_NOMBRE);
                String descripcion = resultSet.getString(DBSchema.PLAN_DESCRIPCION);
                double precio = resultSet.getDouble(DBSchema.PLAN_PRECIO);
                int duracion = resultSet.getInt(DBSchema.PLAN_DURACION);
                boolean activo = resultSet.getBoolean(DBSchema.PLAN_ACTIVO);

                return new Plan(nombre, descripcion, precio, duracion, activo);
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
