package org.luisbaquiax.jwtcaseapi.services.adminusers;


import org.luisbaquiax.jwtcaseapi.dtos.MessageSuccess;
import org.luisbaquiax.jwtcaseapi.dtos.UsuarioComunResponse;
import org.luisbaquiax.jwtcaseapi.models.Rol;

import java.util.List;

public interface AdminUsersService {

    UsuarioComunResponse asignarRol(Long idUsuario, Long idRol);

    List<UsuarioComunResponse> getAllUsers();

    List<UsuarioComunResponse> getAllUsersByStatus(boolean activo);

    List<UsuarioComunResponse> getAllUsersByRol(Rol.NombreRol rol);

    MessageSuccess activarUsuario(Long idUsuario);

    MessageSuccess desactivarUsuario(Long idUsuario);

    List<UsuarioComunResponse> getALlByIds(List<Long> idsUsuarios);

}
