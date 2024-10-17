package org.example.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Usuario implements Serializable {
    private Integer id;
    private String titulo;
    private String genero;
    private Boolean anho;

    private List<Pelicula> peliculaList = new ArrayList<>(0);
}
