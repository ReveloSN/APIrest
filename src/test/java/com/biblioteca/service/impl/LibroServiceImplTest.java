package com.biblioteca.service.impl;

import com.biblioteca.dto.LibroRequest;
import com.biblioteca.dto.LibroResponse;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Libro;
import com.biblioteca.repository.LibroRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    private LibroServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new LibroServiceImpl(libroRepository);
    }

    @Test
    void crearLibroGuardaDatosDelRequest() {
        LibroRequest request = new LibroRequest("978-1", "Clean Code", "Robert Martin", 2008, "Programacion");
        when(libroRepository.save(any(Libro.class))).thenAnswer(invocation -> {
            Libro libro = invocation.getArgument(0);
            libro.setId("libro-1");
            return libro;
        });

        LibroResponse response = service.crearLibro(request);

        assertThat(response.getId()).isEqualTo("libro-1");
        assertThat(response.getTitulo()).isEqualTo("Clean Code");
        verify(libroRepository).save(any(Libro.class));
    }

    @Test
    void consultarLibroInexistenteLanzaNotFound() {
        when(libroRepository.findById("sin-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.consultarLibro("sin-id"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Libro no encontrado");
    }
}
