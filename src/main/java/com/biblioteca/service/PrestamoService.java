package com.biblioteca.service;

import com.biblioteca.dto.PrestamoRequest;
import com.biblioteca.dto.PrestamoResponse;
import java.util.List;

public interface PrestamoService {

    PrestamoResponse crearPrestamo(PrestamoRequest request);

    List<PrestamoResponse> listarPrestamos();

    PrestamoResponse consultarPrestamo(String id);

    PrestamoResponse registrarDevolucion(String id);
}
