package org.example.globbefitnessapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String activo;
    private int idCategoria;
    private Integer idOferta;

    public Producto(String nombre, String descripcion, double precio, int stock, String activo, int idCategoria, Integer idOferta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.activo = activo;
        this.idCategoria = idCategoria;
        this.idOferta = idOferta;
    }
}
