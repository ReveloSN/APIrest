package com.biblioteca.repository;

import com.biblioteca.model.Ejemplar;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EjemplarRepository extends MongoRepository<Ejemplar, String> {

    List<Ejemplar> findByLibroId(String libroId);
}
