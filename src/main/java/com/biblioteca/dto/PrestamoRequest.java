package com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class PrestamoRequest {

    @NotBlank
    private String usuarioId;

    @NotBlank
    private String ejemplarId;

    private LocalDate fechaDevolucionEsperada;

    public PrestamoRequest() {
    }

    public PrestamoRequest(String usuarioId, String ejemplarId, LocalDate fechaDevolucionEsperada) {
        this.usuarioId = usuarioId;
        this.ejemplarId = ejemplarId;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
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

    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }
}
