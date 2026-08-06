package org.luisbaquiax.jwtcaseapi.dtos;

import org.luisbaquiax.jwtcaseapi.models.Rol;

import java.util.List;

public record AutenticacionResponse(
        String token,
        String username,
        Long idUsuario,
        boolean requireDobleFactorAuth,
        boolean primeraVez,
        List<Rol.NombreRol> roles
) {
}
