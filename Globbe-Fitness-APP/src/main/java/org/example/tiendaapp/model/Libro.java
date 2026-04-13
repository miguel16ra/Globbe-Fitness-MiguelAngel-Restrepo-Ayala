package org.example.tiendaapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private long year;
    private String title;
    private String handle;
    private String publisher;
    private String isbn;
    private long pages;
    private List<String> notes;
    private String createdAt;
}
