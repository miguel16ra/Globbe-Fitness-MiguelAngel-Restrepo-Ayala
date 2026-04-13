package org.example.tiendaapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Biblioteca {
    private List<Libro> libros = new ArrayList<Libro>();

    public void agregarLibro(Libro libro){
        libros.add(libro);
    }

    public Libro buscarPorId(int id){
        for(Libro libro : libros){
            if(libro.getId() == id){
                return libro;
            }
        }return null;
    }

    public boolean estaVacio(){
        return libros.isEmpty();
    }

}
