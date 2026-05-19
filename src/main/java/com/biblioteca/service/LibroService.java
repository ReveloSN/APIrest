package com.biblioteca.service;

import com.biblioteca.dto.LibroRequest;
import com.biblioteca.dto.LibroResponse;
import java.util.List;

public interface LibroService {

    LibroResponse crearLibro(LibroRequest request);

    List<LibroResponse> listarLibros();

    LibroResponse consultarLibro(String id);

    LibroResponse actualizarLibro(String id, LibroRequest request);

    void eliminarLibro(String id);
}
