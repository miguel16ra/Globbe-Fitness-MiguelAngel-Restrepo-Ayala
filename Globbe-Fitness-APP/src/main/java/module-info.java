module org.example.globbefitnessapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.net.http;
    requires com.google.gson;
    requires org.json;
    requires java.sql;

    opens org.example.globbefitnessapp to javafx.fxml;
    exports org.example.globbefitnessapp;

    exports org.example.globbefitnessapp.controller;
    opens org.example.globbefitnessapp.controller to javafx.fxml;

    exports org.example.globbefitnessapp.model;
    opens org.example.globbefitnessapp.model to javafx.fxml, com.google.gson, javafx.base;
    exports org.example.globbefitnessapp.controller.admin;
    opens org.example.globbefitnessapp.controller.admin to javafx.fxml;
    exports org.example.globbefitnessapp.controller.user;
    opens org.example.globbefitnessapp.controller.user to javafx.fxml;
}