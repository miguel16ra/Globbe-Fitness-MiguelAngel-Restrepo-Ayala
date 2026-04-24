package org.example.globbefitnessapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Plan {
    private int idPlan;
    private String nombre;
    private String descripcion;
    private double precioMensual;
    private int duracionMeses;
    private boolean activo;

    public Plan(String nombre, String descripcion, double precioMensual, int duracionMeses, boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioMensual = precioMensual;
        this.duracionMeses = duracionMeses;
        this.activo = activo;
    }
}
