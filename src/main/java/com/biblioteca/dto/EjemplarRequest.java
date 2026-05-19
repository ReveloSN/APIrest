package com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;

public class EjemplarRequest {

    @NotBlank
    private String codigoEjemplar;

    @NotBlank
    private String libroId;

    private String estado;

    @NotBlank
    private String ubicacion;

    public EjemplarRequest() {
    }

    public EjemplarRequest(String codigoEjemplar, String libroId, String estado, String ubicacion) {
        this.codigoEjemplar = codigoEjemplar;
        this.libroId = libroId;
        this.estado = estado;
        this.ubicacion = ubicacion;
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
