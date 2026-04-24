package org.example.globbefitnessapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Venta {
    private int idVenta;
    private String fechaVenta;
    private String metodoPago;
    private double total;
    private int idSocio;

    public Venta(String fechaVenta, String metodoPago, double total, int idSocio) {
        this.fechaVenta = fechaVenta;
        this.metodoPago = metodoPago;
        this.total = total;
        this.idSocio = idSocio;
    }
}
