package org.luisbaquiax.jwtcaseapi.services.auth;

import org.luisbaquiax.jwtcaseapi.dtos.AutenticacionResponse;

public interface VerificarTokenService {

    AutenticacionResponse verificarToken(String username, String token);
}
