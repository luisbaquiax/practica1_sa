package org.luisbaquiax.jwtcaseapi.services.adminusers;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.exception.NotFoundException;
import org.luisbaquiax.jwtcaseapi.models.Rol;
import org.luisbaquiax.jwtcaseapi.models.Usuario;
import org.luisbaquiax.jwtcaseapi.repositories.RolRepository;
import org.luisbaquiax.jwtcaseapi.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsuarioRepository repository;
    private final RolRepository rolRepository;

    public Usuario findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado."));
    }

    public Usuario findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado."));
    }

    public Optional<Usuario> getById(Long id) {
        return repository.findById(id);
    }

    public Usuario findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con correo: " + email));
    }

    public Rol findRolById(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado."));
    }

    public boolean existUser(Long idUsuario) {
        return repository.existsById(idUsuario);
    }

    public boolean existUserByIdAndRol(Long idUsuario, String nombreRol) {
        return repository.existsByIdUsuarioAndRole(idUsuario, Rol.NombreRol.valueOf(nombreRol));
    }

}
