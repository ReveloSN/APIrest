package com.biblioteca.dto;

import java.time.LocalDate;

public class PrestamoResponse {

    private String id;
    private String usuarioId;
    private String ejemplarId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private String estado;
    private int diasMora;
    private double mora;

    public PrestamoResponse() {
    }

    public PrestamoResponse(
            String id,
            String usuarioId,
            String ejemplarId,
            LocalDate fechaPrestamo,
            LocalDate fechaDevolucionEsperada,
            LocalDate fechaDevolucionReal,
            String estado,
            int diasMora,
            double mora
    ) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.ejemplarId = ejemplarId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estado = estado;
        this.diasMora = diasMora;
        this.mora = mora;
    }

    public PrestamoResponse(
            String id,
            String usuarioId,
            String ejemplarId,
            LocalDate fechaPrestamo,
            LocalDate fechaDevolucionEsperada,
            LocalDate fechaDevolucionReal,
            String estado
    ) {
        this(id, usuarioId, ejemplarId, fechaPrestamo, fechaDevolucionEsperada, fechaDevolucionReal, estado, 0, 0.0);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEjemplarId() {
        return ejemplarId;
    }

    public void setEjemplarId(String ejemplarId) {
        this.ejemplarId = ejemplarId;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getDiasMora() {
        return diasMora;
    }

    public void setDiasMora(int diasMora) {
        this.diasMora = diasMora;
    }

    public double getMora() {
        return mora;
    }

    public void setMora(double mora) {
        this.mora = mora;
    }
}
