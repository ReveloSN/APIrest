package com.biblioteca.controller;

import com.biblioteca.dto.PrestamoRequest;
import com.biblioteca.dto.PrestamoResponse;
import com.biblioteca.service.PrestamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrestamoController.class)
class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PrestamoService prestamoService;

    @Test
    void crearPrestamoRetornaCreated() throws Exception {
        PrestamoRequest request = new PrestamoRequest("usuario-1", "ejemplar-1", LocalDate.of(2026, 6, 2));
        when(prestamoService.crearPrestamo(any(PrestamoRequest.class)))
                .thenReturn(response());

        mockMvc.perform(post("/api/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("prestamo-1"));
    }

    @Test
    void registrarDevolucionRetornaPrestamoCerrado() throws Exception {
        when(prestamoService.registrarDevolucion("prestamo-1"))
                .thenReturn(new PrestamoResponse(
                        "prestamo-1",
                        "usuario-1",
                        "ejemplar-1",
                        LocalDate.of(2026, 5, 1),
                        LocalDate.of(2026, 5, 15),
                        LocalDate.of(2026, 5, 19),
                        "DEVUELTO",
                        4,
                        4000.0
                ));

        mockMvc.perform(patch("/api/prestamos/prestamo-1/devolucion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("DEVUELTO"))
                .andExpect(jsonPath("$.mora").value(4000.0));
    }

    @Test
    void listarPrestamosPorUsuarioRetornaOk() throws Exception {
        when(prestamoService.listarPrestamosPorUsuario("usuario-1"))
                .thenReturn(List.of(response()));

        mockMvc.perform(get("/api/prestamos/usuario/usuario-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuarioId").value("usuario-1"));
    }

    @Test
    void listarPrestamosPorEjemplarRetornaOk() throws Exception {
        when(prestamoService.listarPrestamosPorEjemplar("ejemplar-1"))
                .thenReturn(List.of(response()));

        mockMvc.perform(get("/api/prestamos/ejemplar/ejemplar-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ejemplarId").value("ejemplar-1"));
    }

    private PrestamoResponse response() {
        return new PrestamoResponse(
                "prestamo-1",
                "usuario-1",
                "ejemplar-1",
                LocalDate.of(2026, 5, 19),
                LocalDate.of(2026, 6, 2),
                null,
                "ACTIVO",
                0,
                0.0
        );
    }
}
