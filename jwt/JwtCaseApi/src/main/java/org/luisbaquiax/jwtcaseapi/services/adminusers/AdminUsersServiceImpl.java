package org.luisbaquiax.jwtcaseapi.services.adminusers;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.dtos.MessageSuccess;
import org.luisbaquiax.jwtcaseapi.dtos.UsuarioComunResponse;
import org.luisbaquiax.jwtcaseapi.exception.BussnessException;
import org.luisbaquiax.jwtcaseapi.mappers.UserMapper;
import org.luisbaquiax.jwtcaseapi.models.Rol;
import org.luisbaquiax.jwtcaseapi.models.Usuario;
import org.luisbaquiax.jwtcaseapi.models.UsuarioRol;
import org.luisbaquiax.jwtcaseapi.repositories.UsuarioRepository;
import org.luisbaquiax.jwtcaseapi.repositories.UsuarioRolRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUsersServiceImpl implements AdminUsersService {

    private final UserService userService;
    private final UserMapper mapper;
    private final UsuarioRepository repository;
    private final UsuarioRolRepository usuarioRolRepository;

    @Override
    public UsuarioComunResponse asignarRol(Long idUsuario, Long idRol) {
        var usuario = userService.findById(idUsuario);
        var rol = userService.findRolById(idRol);

        if(usuarioRolRepository.existsByUsuarioAndRol(usuario, rol))
            throw new BussnessException("El usuario ya tiene asignado este rol.");

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
        usuarioRolRepository.save(usuarioRol);

        return mapper.toUsuarioComunResponse(usuario);
    }

    @Override
    public List<UsuarioComunResponse> getAllUsers() {
        return repository.findAll().stream().map(mapper::toUsuarioComunResponse).toList();
    }

    @Override
    public List<UsuarioComunResponse> getAllUsersByStatus(boolean activo) {
        return repository.findByActivo(activo).stream().map(mapper::toUsuarioComunResponse).toList();
    }

    @Override
    public List<UsuarioComunResponse> getAllUsersByRol(Rol.NombreRol rol) {
        List<Usuario> usuarios = repository.findAll().stream()
                .filter(u -> u.getRoles().stream().anyMatch(r -> r.getRol().getNombre() == rol))
                .toList();
        return usuarios.stream().map(mapper::toUsuarioComunResponse).toList();
    }

    @Override
    public MessageSuccess activarUsuario(Long idUsuario) {
        var usuario = userService.findById(idUsuario);
        usuario.setActivo(true);
        repository.save(usuario);
        return new MessageSuccess("Usuario activado exitosamente.");
    }

    @Override
    public MessageSuccess desactivarUsuario(Long idUsuario) {
        var usuario = userService.findById(idUsuario);
        usuario.setActivo(false);
        repository.save(usuario);
        return new MessageSuccess("Usuario desactivado exitosamente.");
    }

    @Override
    public List<UsuarioComunResponse> getALlByIds(List<Long> idsUsuarios) {
        List<UsuarioComunResponse> listUsuarios = new ArrayList<>();
        for(Long idUsuario: idsUsuarios){
            var usuario = userService.getById(idUsuario);
            if (usuario.isEmpty())
                continue;
            listUsuarios.add(mapper.toUsuarioComunResponse(usuario.get()));
        }

        return listUsuarios;
    }
}
