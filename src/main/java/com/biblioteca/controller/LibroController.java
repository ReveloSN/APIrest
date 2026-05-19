package com.biblioteca.controller;

import com.biblioteca.dto.LibroRequest;
import com.biblioteca.dto.LibroResponse;
import com.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    public ResponseEntity<LibroResponse> crearLibro(@Valid @RequestBody LibroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.crearLibro(request));
    }

    @GetMapping
    public ResponseEntity<List<LibroResponse>> listarLibros() {
        return ResponseEntity.ok(libroService.listarLibros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse> consultarLibro(@PathVariable String id) {
        return ResponseEntity.ok(libroService.consultarLibro(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroResponse> actualizarLibro(
            @PathVariable String id,
            @Valid @RequestBody LibroRequest request
    ) {
        return ResponseEntity.ok(libroService.actualizarLibro(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable String id) {
        libroService.eliminarLibro(id);
        return ResponseEntity.noContent().build();
    }
}
