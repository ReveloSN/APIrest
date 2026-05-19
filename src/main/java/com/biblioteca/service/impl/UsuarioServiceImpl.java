package com.biblioteca.service.impl;

import com.biblioteca.dto.UsuarioRequest;
import com.biblioteca.dto.UsuarioResponse;
import com.biblioteca.exception.BusinessRuleException;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Bibliotecario;
import com.biblioteca.model.Estudiante;
import com.biblioteca.model.Profesor;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.UsuarioService;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        return toResponse(usuarioRepository.save(toEntity(null, request)));
    }

    @Override
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public UsuarioResponse consultarUsuario(String id) {
        return toResponse(findUsuario(id));
    }

    @Override
    public UsuarioResponse actualizarUsuario(String id, UsuarioRequest request) {
        findUsuario(id);
        return toResponse(usuarioRepository.save(toEntity(id, request)));
    }

    @Override
    public void eliminarUsuario(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private Usuario findUsuario(String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    private Usuario toEntity(String id, UsuarioRequest request) {
        String tipo = normalizeTipo(request.getTipoUsuario());
        return switch (tipo) {
            case "ESTUDIANTE" -> new Estudiante(
                    id,
                    request.getNombre(),
                    request.getCorreo(),
                    request.getCodigoEstudiante(),
                    request.getPrograma()
            );
            case "PROFESOR" -> new Profesor(
                    id,
                    request.getNombre(),
                    request.getCorreo(),
                    request.getCodigoProfesor(),
                    request.getFacultad()
            );
            case "BIBLIOTECARIO" -> new Bibliotecario(
                    id,
                    request.getNombre(),
                    request.getCorreo(),
                    request.getCodigoEmpleado(),
                    request.getTurno()
            );
            default -> new Usuario(id, request.getNombre(), request.getCorreo(), tipo);
        };
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTipoUsuario()
        );
        if (usuario instanceof Estudiante estudiante) {
            response.setCodigoEstudiante(estudiante.getCodigoEstudiante());
            response.setPrograma(estudiante.getPrograma());
        }
        if (usuario instanceof Profesor profesor) {
            response.setCodigoProfesor(profesor.getCodigoProfesor());
            response.setFacultad(profesor.getFacultad());
        }
        if (usuario instanceof Bibliotecario bibliotecario) {
            response.setCodigoEmpleado(bibliotecario.getCodigoEmpleado());
            response.setTurno(bibliotecario.getTurno());
        }
        return response;
    }

    private String normalizeTipo(String tipoUsuario) {
        String tipo = tipoUsuario == null ? "" : tipoUsuario.trim().toUpperCase(Locale.ROOT);
        return switch (tipo) {
            case "ESTUDIANTE", "PROFESOR", "BIBLIOTECARIO" -> tipo;
            default -> throw new BusinessRuleException("Tipo de usuario no permitido: " + tipoUsuario);
        };
    }
}
