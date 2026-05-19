package com.biblioteca.model;

public class Profesor extends Usuario {

    private String codigoProfesor;
    private String facultad;

    public Profesor() {
    }

    public Profesor(String id, String nombre, String correo, String codigoProfesor, String facultad) {
        super(id, nombre, correo, "PROFESOR");
        this.codigoProfesor = codigoProfesor;
        this.facultad = facultad;
    }

    public String getCodigoProfesor() {
        return codigoProfesor;
    }

    public void setCodigoProfesor(String codigoProfesor) {
        this.codigoProfesor = codigoProfesor;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }
}
