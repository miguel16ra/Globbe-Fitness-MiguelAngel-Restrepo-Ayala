package org.example.globbefitnessapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Clase {
    private int idClase;
    private String nombre;
    private String descripcion;
    private String fecha;
    private String hora;
    private String sala;
    private int aforoMaximo;
    private String monitor;
    private String estado;

    public Clase(String nombre, String descripcion, String fecha, String hora, String sala, int aforoMaximo, String monitor, String estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.sala = sala;
        this.aforoMaximo = aforoMaximo;
        this.monitor = monitor;
        this.estado = estado;
    }
}
