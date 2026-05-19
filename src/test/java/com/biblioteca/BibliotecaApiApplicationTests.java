package com.biblioteca;

import com.biblioteca.repository.EjemplarRepository;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(properties = "spring.autoconfigure.exclude="
        + "org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,"
        + "org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,"
        + "org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration")
class BibliotecaApiApplicationTests {

    @MockBean
    private LibroRepository libroRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private EjemplarRepository ejemplarRepository;

    @MockBean
    private PrestamoRepository prestamoRepository;

    @Test
    void contextLoads() {
    }
}
