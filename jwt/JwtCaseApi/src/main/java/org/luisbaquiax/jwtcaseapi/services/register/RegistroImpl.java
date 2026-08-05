package org.luisbaquiax.jwtcaseapi.services.register;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.dtos.UsuarioComunRequest;
import org.luisbaquiax.jwtcaseapi.dtos.UsuarioComunResponse;
import org.luisbaquiax.jwtcaseapi.exception.DuplicateEmailException;
import org.luisbaquiax.jwtcaseapi.exception.DuplicateUsernameException;
import org.luisbaquiax.jwtcaseapi.exception.NotFoundException;
import org.luisbaquiax.jwtcaseapi.mappers.UserMapper;
import org.luisbaquiax.jwtcaseapi.models.Rol;
import org.luisbaquiax.jwtcaseapi.models.Usuario;
import org.luisbaquiax.jwtcaseapi.models.UsuarioRol;
import org.luisbaquiax.jwtcaseapi.repositories.RolRepository;
import org.luisbaquiax.jwtcaseapi.repositories.UsuarioRepository;
import org.luisbaquiax.jwtcaseapi.repositories.UsuarioRolRepository;
import org.luisbaquiax.jwtcaseapi.utils.Encriptation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RegistroImpl implements RegistroService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UserMapper mapper;
    private final Encriptation encriptation;

    @Transactional
    @Override
    public UsuarioComunResponse register(UsuarioComunRequest request) {
        if(usuarioRepository.existsByUsername(request.username()))
            throw new DuplicateUsernameException("Nombre de usuario se encuentra en uso, por favor elija otro.");
        if(usuarioRepository.existsByEmail(request.email()))
            throw new DuplicateEmailException("Correo electrónico se encuentra en uso, por favor elija otro.");

        Usuario nuevoUsuario = mapper.toEntity(request);
        nuevoUsuario.setActivo(true);
        nuevoUsuario.setDobleFactorAuth(false);
        nuevoUsuario.setPrimeraVez(false);
        nuevoUsuario.setMetodo2FA(Usuario.MetodoAutenticacion.PASSWORD);
        nuevoUsuario.setPasswordHash(encriptation.encriptPassword(request.password()));

        Rol rol = rolRepository.findByNombre(Rol.NombreRol.ROLE_USUARIO).orElseThrow(() -> new NotFoundException("Rol no encontrado."));

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        usuarioRolRepository.save(new UsuarioRol(null, usuarioGuardado, LocalDate.now(), rol));

        return mapper.toUsuarioComunResponse(usuarioGuardado);
    }
}
