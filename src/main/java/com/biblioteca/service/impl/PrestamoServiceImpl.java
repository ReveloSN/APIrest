package com.biblioteca.service.impl;

import com.biblioteca.dto.PrestamoRequest;
import com.biblioteca.dto.PrestamoResponse;
import com.biblioteca.exception.BusinessRuleException;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Ejemplar;
import com.biblioteca.model.Prestamo;
import com.biblioteca.repository.EjemplarRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.PrestamoService;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private static final String ESTADO_DISPONIBLE = "DISPONIBLE";
    private static final String ESTADO_PRESTADO = "PRESTADO";
    private static final String ESTADO_ACTIVO = "ACTIVO";
    private static final String ESTADO_DEVUELTO = "DEVUELTO";

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EjemplarRepository ejemplarRepository;
    private final Clock clock;

    @Autowired
    public PrestamoServiceImpl(
            PrestamoRepository prestamoRepository,
            UsuarioRepository usuarioRepository,
            EjemplarRepository ejemplarRepository
    ) {
        this(prestamoRepository, usuarioRepository, ejemplarRepository, Clock.systemDefaultZone());
    }

    public PrestamoServiceImpl(
            PrestamoRepository prestamoRepository,
            UsuarioRepository usuarioRepository,
            EjemplarRepository ejemplarRepository,
            Clock clock
    ) {
        this.prestamoRepository = prestamoRepository;
        this.usuarioRepository = usuarioRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.clock = clock;
    }

    @Override
    public PrestamoResponse crearPrestamo(PrestamoRequest request) {
        validarUsuario(request.getUsuarioId());
        Ejemplar ejemplar = findEjemplar(request.getEjemplarId());
        if (!ESTADO_DISPONIBLE.equalsIgnoreCase(ejemplar.getEstado())) {
            throw new BusinessRuleException("El ejemplar no esta disponible para prestamo");
        }

        LocalDate fechaPrestamo = LocalDate.now(clock);
        LocalDate fechaDevolucionEsperada = request.getFechaDevolucionEsperada() == null
                ? fechaPrestamo.plusDays(14)
                : request.getFechaDevolucionEsperada();

        Prestamo prestamo = new Prestamo(
                null,
                request.getUsuarioId(),
                request.getEjemplarId(),
                fechaPrestamo,
                fechaDevolucionEsperada,
                null,
                ESTADO_ACTIVO
        );
        Prestamo saved = prestamoRepository.save(prestamo);
        ejemplar.setEstado(ESTADO_PRESTADO);
        ejemplarRepository.save(ejemplar);
        return toResponse(saved);
    }

    @Override
    public List<PrestamoResponse> listarPrestamos() {
        return prestamoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<PrestamoResponse> listarPrestamosPorUsuario(String usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId).stream().map(this::toResponse).toList();
    }

    @Override
    public List<PrestamoResponse> listarPrestamosPorEjemplar(String ejemplarId) {
        return prestamoRepository.findByEjemplarId(ejemplarId).stream().map(this::toResponse).toList();
    }

    @Override
    public PrestamoResponse consultarPrestamo(String id) {
        return toResponse(findPrestamo(id));
    }

    @Override
    public PrestamoResponse registrarDevolucion(String id) {
        Prestamo prestamo = findPrestamo(id);
        if (!ESTADO_ACTIVO.equalsIgnoreCase(prestamo.getEstado())) {
            throw new BusinessRuleException("El prestamo no esta activo");
        }
        Ejemplar ejemplar = findEjemplar(prestamo.getEjemplarId());

        prestamo.setEstado(ESTADO_DEVUELTO);
        prestamo.setFechaDevolucionReal(LocalDate.now(clock));
        Prestamo saved = prestamoRepository.save(prestamo);

        ejemplar.setEstado(ESTADO_DISPONIBLE);
        ejemplarRepository.save(ejemplar);
        return toResponse(saved);
    }

    private void validarUsuario(String usuarioId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));
    }

    private Ejemplar findEjemplar(String id) {
        return ejemplarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ejemplar no encontrado con id: " + id));
    }

    private Prestamo findPrestamo(String id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestamo no encontrado con id: " + id));
    }

    private PrestamoResponse toResponse(Prestamo prestamo) {
        LocalDate fechaReferencia = prestamo.getFechaDevolucionReal() == null
                ? LocalDate.now(clock)
                : prestamo.getFechaDevolucionReal();
        return new PrestamoResponse(
                prestamo.getId(),
                prestamo.getUsuarioId(),
                prestamo.getEjemplarId(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucionEsperada(),
                prestamo.getFechaDevolucionReal(),
                prestamo.getEstado(),
                prestamo.calcularDiasMora(fechaReferencia),
                prestamo.calcularMora(fechaReferencia)
        );
    }
}
