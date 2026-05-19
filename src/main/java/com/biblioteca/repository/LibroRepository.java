package com.biblioteca.repository;

import com.biblioteca.model.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibroRepository extends MongoRepository<Libro, String> {
}
