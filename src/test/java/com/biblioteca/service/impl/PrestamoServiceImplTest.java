package com.biblioteca.service.impl;

import com.biblioteca.dto.PrestamoRequest;
import com.biblioteca.dto.PrestamoResponse;
import com.biblioteca.exception.BusinessRuleException;
import com.biblioteca.model.Ejemplar;
import com.biblioteca.model.Prestamo;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.EjemplarRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceImplTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-19T12:00:00Z"),
            ZoneId.of("America/Bogota")
    );

    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EjemplarRepository ejemplarRepository;

    private PrestamoServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PrestamoServiceImpl(prestamoRepository, usuarioRepository, ejemplarRepository, FIXED_CLOCK);
    }

    @Test
    void crearPrestamoMarcaElEjemplarComoPrestadoYGuardaPrestamoActivo() {
        Usuario usuario = new Usuario("usuario-1", "Ana Torres", "ana@uni.edu", "ESTUDIANTE");
        Ejemplar ejemplar = new Ejemplar("ejemplar-1", "EJ-001", "libro-1", "DISPONIBLE", "Sala A");
        PrestamoRequest request = new PrestamoRequest("usuario-1", "ejemplar-1", LocalDate.of(2026, 6, 2));

        when(usuarioRepository.findById("usuario-1")).thenReturn(Optional.of(usuario));
        when(ejemplarRepository.findById("ejemplar-1")).thenReturn(Optional.of(ejemplar));
        when(prestamoRepository.save(any(Prestamo.class))).thenAnswer(invocation -> {
            Prestamo prestamo = invocation.getArgument(0);
            prestamo.setId("prestamo-1");
            return prestamo;
        });

        PrestamoResponse response = service.crearPrestamo(request);

        assertThat(response.getId()).isEqualTo("prestamo-1");
        assertThat(response.getUsuarioId()).isEqualTo("usuario-1");
        assertThat(response.getEjemplarId()).isEqualTo("ejemplar-1");
        assertThat(response.getFechaPrestamo()).isEqualTo(LocalDate.of(2026, 5, 19));
        assertThat(response.getFechaDevolucionEsperada()).isEqualTo(LocalDate.of(2026, 6, 2));
        assertThat(response.getEstado()).isEqualTo("ACTIVO");
        verify(ejemplarRepository).save(argThat(saved -> "PRESTADO".equals(saved.getEstado())));
    }

    @Test
    void crearPrestamoRechazaEjemplarNoDisponible() {
        Usuario usuario = new Usuario("usuario-1", "Ana Torres", "ana@uni.edu", "ESTUDIANTE");
        Ejemplar ejemplar = new Ejemplar("ejemplar-1", "EJ-001", "libro-1", "PRESTADO", "Sala A");
        PrestamoRequest request = new PrestamoRequest("usuario-1", "ejemplar-1", LocalDate.of(2026, 6, 2));

        when(usuarioRepository.findById("usuario-1")).thenReturn(Optional.of(usuario));
        when(ejemplarRepository.findById("ejemplar-1")).thenReturn(Optional.of(ejemplar));

        assertThatThrownBy(() -> service.crearPrestamo(request))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("no esta disponible");

        verify(prestamoRepository, never()).save(any(Prestamo.class));
        verify(ejemplarRepository, never()).save(any(Ejemplar.class));
    }

    @Test
    void registrarDevolucionCierraPrestamoYMarcaEjemplarDisponible() {
        Prestamo prestamo = new Prestamo(
                "prestamo-1",
                "usuario-1",
                "ejemplar-1",
                LocalDate.of(2026, 5, 1),
                LocalDate.of(2026, 5, 15),
                null,
                "ACTIVO"
        );
        Ejemplar ejemplar = new Ejemplar("ejemplar-1", "EJ-001", "libro-1", "PRESTADO", "Sala A");

        when(prestamoRepository.findById("prestamo-1")).thenReturn(Optional.of(prestamo));
        when(ejemplarRepository.findById("ejemplar-1")).thenReturn(Optional.of(ejemplar));
        when(prestamoRepository.save(any(Prestamo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PrestamoResponse response = service.registrarDevolucion("prestamo-1");

        assertThat(response.getEstado()).isEqualTo("DEVUELTO");
        assertThat(response.getFechaDevolucionReal()).isEqualTo(LocalDate.of(2026, 5, 19));
        verify(ejemplarRepository).save(argThat(saved -> "DISPONIBLE".equals(saved.getEstado())));
    }
}
