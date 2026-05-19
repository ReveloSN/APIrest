package com.biblioteca.service.impl;

import com.biblioteca.dto.EjemplarRequest;
import com.biblioteca.dto.EjemplarResponse;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Ejemplar;
import com.biblioteca.repository.EjemplarRepository;
import com.biblioteca.repository.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EjemplarServiceImplTest {

    @Mock
    private EjemplarRepository ejemplarRepository;

    @Mock
    private LibroRepository libroRepository;

    private EjemplarServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new EjemplarServiceImpl(ejemplarRepository, libroRepository);
    }

    @Test
    void crearEjemplarUsaEstadoDisponiblePorDefecto() {
        EjemplarRequest request = new EjemplarRequest("EJ-001", "libro-1", null, "Sala A");
        when(libroRepository.existsById("libro-1")).thenReturn(true);
        when(ejemplarRepository.save(any(Ejemplar.class))).thenAnswer(invocation -> {
            Ejemplar ejemplar = invocation.getArgument(0);
            ejemplar.setId("ejemplar-1");
            return ejemplar;
        });

        EjemplarResponse response = service.crearEjemplar(request);

        assertThat(response.getEstado()).isEqualTo("DISPONIBLE");
        assertThat(response.getLibroId()).isEqualTo("libro-1");
    }

    @Test
    void crearEjemplarRechazaLibroInexistente() {
        EjemplarRequest request = new EjemplarRequest("EJ-001", "libro-x", null, "Sala A");
        when(libroRepository.existsById("libro-x")).thenReturn(false);

        assertThatThrownBy(() -> service.crearEjemplar(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Libro no encontrado");

        verify(ejemplarRepository, never()).save(any(Ejemplar.class));
    }
}
