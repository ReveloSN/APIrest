package com.biblioteca.controller;

import com.biblioteca.dto.EjemplarRequest;
import com.biblioteca.dto.EjemplarResponse;
import com.biblioteca.service.EjemplarService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EjemplarController.class)
class EjemplarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EjemplarService ejemplarService;

    @Test
    void crearEjemplarRetornaCreated() throws Exception {
        EjemplarRequest request = new EjemplarRequest("EJ-001", "libro-1", "DISPONIBLE", "Sala A");
        when(ejemplarService.crearEjemplar(any(EjemplarRequest.class)))
                .thenReturn(new EjemplarResponse("ejemplar-1", "EJ-001", "libro-1", "DISPONIBLE", "Sala A"));

        mockMvc.perform(post("/api/ejemplares")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("ejemplar-1"));
    }

    @Test
    void listarEjemplaresRetornaOk() throws Exception {
        when(ejemplarService.listarEjemplares())
                .thenReturn(List.of(new EjemplarResponse("ejemplar-1", "EJ-001", "libro-1", "DISPONIBLE", "Sala A")));

        mockMvc.perform(get("/api/ejemplares"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("DISPONIBLE"));
    }
}
