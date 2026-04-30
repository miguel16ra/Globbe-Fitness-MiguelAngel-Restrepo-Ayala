package org.example.globbefitnessapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carrito {
    private int idProducto;
    private String nombre;
    private double precio;
    private int cantidad;

    public double getSubtotal() {
        return precio * cantidad;
    }
}
