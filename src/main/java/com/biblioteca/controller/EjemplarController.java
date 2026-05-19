package com.biblioteca.controller;

import com.biblioteca.dto.EjemplarRequest;
import com.biblioteca.dto.EjemplarResponse;
import com.biblioteca.service.EjemplarService;
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
@RequestMapping("/api/ejemplares")
public class EjemplarController {

    private final EjemplarService ejemplarService;

    public EjemplarController(EjemplarService ejemplarService) {
        this.ejemplarService = ejemplarService;
    }

    @PostMapping
    public ResponseEntity<EjemplarResponse> crearEjemplar(@Valid @RequestBody EjemplarRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ejemplarService.crearEjemplar(request));
    }

    @GetMapping
    public ResponseEntity<List<EjemplarResponse>> listarEjemplares() {
        return ResponseEntity.ok(ejemplarService.listarEjemplares());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EjemplarResponse> consultarEjemplar(@PathVariable String id) {
        return ResponseEntity.ok(ejemplarService.consultarEjemplar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EjemplarResponse> actualizarEjemplar(
            @PathVariable String id,
            @Valid @RequestBody EjemplarRequest request
    ) {
        return ResponseEntity.ok(ejemplarService.actualizarEjemplar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEjemplar(@PathVariable String id) {
        ejemplarService.eliminarEjemplar(id);
        return ResponseEntity.noContent().build();
    }
}
