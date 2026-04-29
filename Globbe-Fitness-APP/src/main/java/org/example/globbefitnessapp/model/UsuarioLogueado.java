package org.example.globbefitnessapp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class UsuarioLogueado {

    private static Usuario usuario;

    public static void cerrarSesion(){
        usuario=null;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        UsuarioLogueado.usuario = usuario;
    }
}
