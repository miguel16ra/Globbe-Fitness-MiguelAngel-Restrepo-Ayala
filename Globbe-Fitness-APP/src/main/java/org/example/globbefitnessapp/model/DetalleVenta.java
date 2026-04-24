package org.example.globbefitnessapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DetalleVenta {
    private int idDetalle;
    private int cantidad;
    private double precioUnitario;
    private double total;
    private int idVenta;
    private int idProducto;

    public DetalleVenta(int cantidad, double precioUnitario, double total, int idVenta, int idProducto) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
    }
}
