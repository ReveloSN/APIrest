package com.biblioteca.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ejemplares")
public class Ejemplar {

    @Id
    private String id;
    private String codigoEjemplar;
    private String libroId;
    private String estado;
    private String ubicacion;

    public Ejemplar() {
    }

    public Ejemplar(String id, String codigoEjemplar, String libroId, String estado, String ubicacion) {
        this.id = id;
        this.codigoEjemplar = codigoEjemplar;
        this.libroId = libroId;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigoEjemplar() {
        return codigoEjemplar;
    }

    public void setCodigoEjemplar(String codigoEjemplar) {
        this.codigoEjemplar = codigoEjemplar;
    }

    public String getLibroId() {
        return libroId;
    }

    public void setLibroId(String libroId) {
        this.libroId = libroId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
