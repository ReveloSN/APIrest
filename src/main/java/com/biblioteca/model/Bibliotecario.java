package com.biblioteca.model;

public class Bibliotecario extends Usuario {

    private String codigoEmpleado;
    private String turno;

    public Bibliotecario() {
    }

    public Bibliotecario(String id, String nombre, String correo, String codigoEmpleado, String turno) {
        super(id, nombre, correo, "BIBLIOTECARIO");
        this.codigoEmpleado = codigoEmpleado;
        this.turno = turno;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
