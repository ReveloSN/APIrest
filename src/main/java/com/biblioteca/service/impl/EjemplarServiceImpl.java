package com.biblioteca.service.impl;

import com.biblioteca.dto.EjemplarRequest;
import com.biblioteca.dto.EjemplarResponse;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Ejemplar;
import com.biblioteca.repository.EjemplarRepository;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.service.EjemplarService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EjemplarServiceImpl implements EjemplarService {

    private static final String ESTADO_DISPONIBLE = "DISPONIBLE";

    private final EjemplarRepository ejemplarRepository;
    private final LibroRepository libroRepository;

    public EjemplarServiceImpl(EjemplarRepository ejemplarRepository, LibroRepository libroRepository) {
        this.ejemplarRepository = ejemplarRepository;
        this.libroRepository = libroRepository;
    }

    @Override
    public EjemplarResponse crearEjemplar(EjemplarRequest request) {
        validarLibro(request.getLibroId());
        String estado = request.getEstado() == null || request.getEstado().isBlank()
                ? ESTADO_DISPONIBLE
                : request.getEstado().trim().toUpperCase();
        Ejemplar ejemplar = new Ejemplar(null, request.getCodigoEjemplar(), request.getLibroId(),
                estado, request.getUbicacion());
        return toResponse(ejemplarRepository.save(ejemplar));
    }

    @Override
    public List<EjemplarResponse> listarEjemplares() {
        return ejemplarRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public EjemplarResponse consultarEjemplar(String id) {
        return toResponse(findEjemplar(id));
    }

    @Override
    public EjemplarResponse actualizarEjemplar(String id, EjemplarRequest request) {
        validarLibro(request.getLibroId());
        Ejemplar ejemplar = findEjemplar(id);
        ejemplar.setCodigoEjemplar(request.getCodigoEjemplar());
        ejemplar.setLibroId(request.getLibroId());
        ejemplar.setEstado(request.getEstado() == null ? ejemplar.getEstado() : request.getEstado().trim().toUpperCase());
        ejemplar.setUbicacion(request.getUbicacion());
        return toResponse(ejemplarRepository.save(ejemplar));
    }

    @Override
    public void eliminarEjemplar(String id) {
        if (!ejemplarRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ejemplar no encontrado con id: " + id);
        }
        ejemplarRepository.deleteById(id);
    }

    private void validarLibro(String libroId) {
        if (!libroRepository.existsById(libroId)) {
            throw new ResourceNotFoundException("Libro no encontrado con id: " + libroId);
        }
    }

    private Ejemplar findEjemplar(String id) {
        return ejemplarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ejemplar no encontrado con id: " + id));
    }

    private EjemplarResponse toResponse(Ejemplar ejemplar) {
        return new EjemplarResponse(
                ejemplar.getId(),
                ejemplar.getCodigoEjemplar(),
                ejemplar.getLibroId(),
                ejemplar.getEstado(),
                ejemplar.getUbicacion()
        );
    }
}
