package org.example.globbefitnessapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
//            System.out.println("Conectada");
        }
        return connection;
    }

    private static void createConnection() {
        String user = "root";
        String pass = "";
        String url = "127.0.0.1";
        String port = "3306";
        String dbName = "globbe_fitness";
        String urlJDBC = String.format("jdbc:mariadb://%s:%s/%s", url, port, dbName);

        try {
            connection = DriverManager.getConnection(urlJDBC,user,pass);
        }catch (SQLException e){
            System.out.println("Error en la conexion con la base de datos");
            System.out.println(e.getMessage());
        }
    }
}
