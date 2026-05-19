package com.biblioteca.model;

public class Estudiante extends Usuario {

    private String codigoEstudiante;
    private String programa;

    public Estudiante() {
    }

    public Estudiante(String id, String nombre, String correo, String codigoEstudiante, String programa) {
        super(id, nombre, correo, "ESTUDIANTE");
        this.codigoEstudiante = codigoEstudiante;
        this.programa = programa;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }
}
