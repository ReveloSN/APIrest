package com.biblioteca.service.impl;

import com.biblioteca.dto.LibroRequest;
import com.biblioteca.dto.LibroResponse;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Libro;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.service.LibroService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public LibroResponse crearLibro(LibroRequest request) {
        Libro libro = new Libro(null, request.getIsbn(), request.getTitulo(), request.getAutor(),
                request.getAnioPublicacion(), request.getCategoria());
        return toResponse(libroRepository.save(libro));
    }

    @Override
    public List<LibroResponse> listarLibros() {
        return libroRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public LibroResponse consultarLibro(String id) {
        return toResponse(findLibro(id));
    }

    @Override
    public LibroResponse actualizarLibro(String id, LibroRequest request) {
        Libro libro = findLibro(id);
        libro.setIsbn(request.getIsbn());
        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setAnioPublicacion(request.getAnioPublicacion());
        libro.setCategoria(request.getCategoria());
        return toResponse(libroRepository.save(libro));
    }

    @Override
    public void eliminarLibro(String id) {
        if (!libroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Libro no encontrado con id: " + id);
        }
        libroRepository.deleteById(id);
    }

    private Libro findLibro(String id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));
    }

    private LibroResponse toResponse(Libro libro) {
        return new LibroResponse(
                libro.getId(),
                libro.getIsbn(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getAnioPublicacion(),
                libro.getCategoria()
        );
    }
}
