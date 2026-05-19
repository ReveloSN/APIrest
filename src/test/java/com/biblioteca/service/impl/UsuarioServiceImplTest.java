package com.biblioteca.service.impl;

import com.biblioteca.dto.UsuarioRequest;
import com.biblioteca.dto.UsuarioResponse;
import com.biblioteca.exception.BusinessRuleException;
import com.biblioteca.model.Estudiante;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private UsuarioServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new UsuarioServiceImpl(usuarioRepository);
    }

    @Test
    void crearEstudianteGuardaCamposEspecificos() {
        UsuarioRequest request = new UsuarioRequest("Ana Torres", "ana@uni.edu", "ESTUDIANTE");
        request.setCodigoEstudiante("EST-01");
        request.setPrograma("Ingenieria");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Estudiante estudiante = invocation.getArgument(0);
            estudiante.setId("usuario-1");
            return estudiante;
        });

        UsuarioResponse response = service.crearUsuario(request);

        assertThat(response.getId()).isEqualTo("usuario-1");
        assertThat(response.getTipoUsuario()).isEqualTo("ESTUDIANTE");
        assertThat(response.getCodigoEstudiante()).isEqualTo("EST-01");
        assertThat(response.getPrograma()).isEqualTo("Ingenieria");
    }

    @Test
    void crearUsuarioRechazaTipoNoPermitido() {
        UsuarioRequest request = new UsuarioRequest("Invitado", "invitado@uni.edu", "INVITADO");

        assertThatThrownBy(() -> service.crearUsuario(request))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Tipo de usuario no permitido");

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
