package org.example.globbefitnessapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {
    private int id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String password;
    private String rol;

    public Usuario(String nombre, String apellidos, String correo, String password, String rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    public Usuario(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }
}
