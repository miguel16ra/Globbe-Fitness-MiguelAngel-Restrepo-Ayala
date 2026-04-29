package org.example.globbefitnessapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaTabla {
    private int idReserva;
    private String nombre;
    private String fecha;
    private String hora;
    private String sala;
    private String monitor;
    private String estado;
    private String asistencia;
}
