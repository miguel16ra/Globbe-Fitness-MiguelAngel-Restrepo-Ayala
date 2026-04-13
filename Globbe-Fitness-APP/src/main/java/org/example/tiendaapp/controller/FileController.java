package org.example.tiendaapp.controller;

import org.example.tiendaapp.model.Libro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileController {

    public void exportarFavoritos(List<Libro> lista) {
        File file = new File("favoritos.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            for (Libro libro : lista) {
                bw.write("ID: " + libro.getId());
                bw.newLine();
                bw.write("Título: " + libro.getTitle());
                bw.newLine();
                bw.write("Año: " + libro.getYear());
                bw.newLine();
                bw.write("Editorial: " + libro.getPublisher());
                bw.newLine();
                bw.write("Páginas: " + libro.getPages());
                bw.newLine();
                bw.write("-----------------------------");
                bw.newLine();
            }

            System.out.println("Favoritos exportados correctamente en TXT");

        } catch (IOException e) {
            System.out.println("Error en el proceso de escritura del fichero");
        }
    }

    public List<Libro> importarFavoritos() {
        File file = new File("favoritos.txt");
        List<Libro> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String linea;
            Libro libro = null;

            while ((linea = br.readLine()) != null) {

                if (linea.startsWith("ID: ")) {
                    libro = new Libro();
                    libro.setId(Long.parseLong(linea.substring(4)));
                } else if (linea.startsWith("Título: ")) {
                    libro.setTitle(linea.substring(8));
                } else if (linea.startsWith("Año: ")) {
                    libro.setYear(Long.parseLong(linea.substring(5)));
                } else if (linea.startsWith("Editorial: ")) {
                    libro.setPublisher(linea.substring(11));
                } else if (linea.startsWith("Páginas: ")) {
                    libro.setPages(Long.parseLong(linea.substring(9)));
                } else if (linea.startsWith("-----------------------------")) {
                    if (libro != null) {
                        lista.add(libro);
                    }
                }
            }

            System.out.println("Favoritos importados correctamente desde TXT");

        } catch (IOException e) {
            System.out.println("Error en la lectura del fichero");
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir datos numéricos del fichero");
        }

        return lista;
    }
}
