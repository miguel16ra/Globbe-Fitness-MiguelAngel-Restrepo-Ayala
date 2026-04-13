package org.example.tiendaapp.controller;

import com.google.gson.Gson;
import org.example.tiendaapp.model.Libro;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class APIController {

    public List<Libro> getLibros() {
        List<Libro> listaLibros = new ArrayList<>();
        String url = "https://stephen-king-api.onrender.com/api/books";

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String stringLibros = response.body();

            JSONObject jsonLibros = new JSONObject(stringLibros);
            JSONArray arrayLibros = jsonLibros.getJSONArray("data");

            for (int i = 0; i < arrayLibros.length(); i++) {
                JSONObject libroJSON = arrayLibros.getJSONObject(i);

                Libro libro = new Libro();
                libro.setId(libroJSON.optLong("id", 0));
                libro.setYear(libroJSON.optLong("Year", 0));
                libro.setTitle(libroJSON.optString("Title", ""));
                libro.setHandle(libroJSON.optString("handle", ""));
                libro.setPublisher(libroJSON.optString("Publisher", ""));
                libro.setIsbn(libroJSON.optString("ISBN", ""));
                libro.setPages(libroJSON.optLong("Pages", 0));
                libro.setCreatedAt(libroJSON.optString("created_at", ""));

                listaLibros.add(libro);
            }

        } catch (Exception e) {
            System.out.println("Error en la petición HTTP: " + e.getMessage());
        }

        return listaLibros;
    }

    public void getLibroById(int id) {
        String url = "https://stephen-king-api.onrender.com/api/book/" + id;

        try {
            Gson gson = new Gson();
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String stringLibro = response.body();

            JSONObject jsonLibro = new JSONObject(stringLibro);
            JSONObject libroJSON = jsonLibro.getJSONObject("data");

            Libro libro = gson.fromJson(libroJSON.toString(), Libro.class);
            System.out.println("Título: " + libro.getTitle());
            System.out.println("Año: " + libro.getYear());
            System.out.println("Editorial: " + libro.getPublisher());
            System.out.println("Notas: " + libro.getNotes());
        } catch (Exception e) {
            System.out.printf("Error en el procesamiento de la peticion");
        }
    }
}
