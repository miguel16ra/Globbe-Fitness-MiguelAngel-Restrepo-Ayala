package org.example.globbefitnessapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Socio {
    private int idSocio;
    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private String telefono;
    private String fechaAlta;
    private boolean estado;
    private int idPlan;

    public Socio(String nombre, String apellidos, String dni, String email, String telefono, String fechaAlta, boolean estado, int idPlan) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fechaAlta = fechaAlta;
        this.estado = estado;
        this.idPlan = idPlan;
    }
}
