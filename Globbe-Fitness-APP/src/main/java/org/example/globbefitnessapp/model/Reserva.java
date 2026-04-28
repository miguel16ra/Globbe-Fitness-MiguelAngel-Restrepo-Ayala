package org.example.globbefitnessapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Reserva {
    private int idReserva;
    private String fechaReserva;
    private String estado;
    private String asistencia;
    private int idSocio;
    private int idClase;

    public Reserva(String fechaReserva, String estado, String asistencia, int idSocio, int idClase) {
        this.fechaReserva = fechaReserva;
        this.estado = estado;
        this.asistencia = asistencia;
        this.idSocio = idSocio;
        this.idClase = idClase;
    }
}
