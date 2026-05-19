package com.biblioteca.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prestamos")
public class Prestamo {

    @Id
    private String id;
    private String usuarioId;
    private String ejemplarId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private String estado;

    public Prestamo() {
    }

    public Prestamo(
            String id,
            String usuarioId,
            String ejemplarId,
            LocalDate fechaPrestamo,
            LocalDate fechaDevolucionEsperada,
            LocalDate fechaDevolucionReal,
            String estado
    ) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.ejemplarId = ejemplarId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estado = estado;
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

    public int calcularDiasMora(LocalDate fechaReferencia) {
        if (fechaDevolucionEsperada == null || fechaReferencia == null) {
            return 0;
        }
        if (!fechaReferencia.isAfter(fechaDevolucionEsperada)) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(fechaDevolucionEsperada, fechaReferencia);
    }

    public double calcularMora(LocalDate fechaReferencia) {
        return calcularDiasMora(fechaReferencia) * 1000.0;
    }
}
