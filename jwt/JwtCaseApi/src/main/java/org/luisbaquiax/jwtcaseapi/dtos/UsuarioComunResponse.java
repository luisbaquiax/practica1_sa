package org.luisbaquiax.jwtcaseapi.dtos;

import org.luisbaquiax.jwtcaseapi.models.Usuario;

import java.time.LocalDate;
import java.util.List;

public record UsuarioComunResponse(
        Long idUsuario,
        String username,
        String email,
        String nombres,
        String apellidos,
        String telefono,
        LocalDate fechaNacimiento,
        boolean activo,
        boolean dobleFactorAuth,
        Usuario.MetodoAutenticacion metodo2FA,
        List<RolDTO> roles
) {
}
