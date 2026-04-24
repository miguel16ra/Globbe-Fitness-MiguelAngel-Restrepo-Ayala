package org.example.globbefitnessapp;

import org.example.globbefitnessapp.database.DBConnection;

import java.sql.Connection;

public class MainPruebas {

    static void main(String[] args) {
        Connection connection = DBConnection.getConnection();

    }
}
