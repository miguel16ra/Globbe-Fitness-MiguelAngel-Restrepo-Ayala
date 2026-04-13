package org.example.tiendaapp.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tiendaapp.model.Biblioteca;
import org.example.tiendaapp.model.Libro;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class BibliotecaController {

    private APIController apiController;
    private Biblioteca biblioteca;
    private List<Libro> favoritos;

    public BibliotecaController() {
        this.apiController = new APIController();
        this.biblioteca = new Biblioteca();
        this.favoritos = new ArrayList<>();
    }

    public void cargarLibros() {
        List<Libro> librosAPI = apiController.getLibros();
        biblioteca.setLibros(librosAPI);
    }

    public List<Libro> getLibros() {
        return biblioteca.getLibros();
    }

    public Libro buscarLibroEnBiblioteca(long id) {
        for (Libro libro : biblioteca.getLibros()) {
            if (libro.getId() == id) {
                return libro;
            }
        }
        return null;
    }

    public boolean agregarFavorito(long id) {
        Libro libro = buscarLibroEnBiblioteca(id);

        if (libro != null && !favoritos.contains(libro)) {
            favoritos.add(libro);
            return true;
        }

        return false;
    }

    public List<Libro> getFavoritos() {
        return favoritos;
    }

}
