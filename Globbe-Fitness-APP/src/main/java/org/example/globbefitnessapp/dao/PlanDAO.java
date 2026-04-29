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

    public List<Plan> getAllPlanes() throws SQLException {
        List<Plan> listaPlanes = new ArrayList<>();
        connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM %s",
                DBSchema.TAB_PLAN);


        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            int id = resultSet.getInt(DBSchema.PLAN_ID);
            String nombre = resultSet.getString(DBSchema.PLAN_NOMBRE);
            String descripcion = resultSet.getString(DBSchema.PLAN_DESCRIPCION);
            double precio = resultSet.getDouble(DBSchema.PLAN_PRECIO);
            int duracion = resultSet.getInt(DBSchema.PLAN_DURACION);
            String activo = resultSet.getString(DBSchema.PLAN_ACTIVO);

            listaPlanes.add(new Plan(id,nombre,descripcion,precio,duracion,activo));
        }

        return listaPlanes;
    }
}
