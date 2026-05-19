package com.biblioteca.repository;

import com.biblioteca.model.Prestamo;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrestamoRepository extends MongoRepository<Prestamo, String> {

    List<Prestamo> findByUsuarioId(String usuarioId);

    List<Prestamo> findByEjemplarId(String ejemplarId);
}
