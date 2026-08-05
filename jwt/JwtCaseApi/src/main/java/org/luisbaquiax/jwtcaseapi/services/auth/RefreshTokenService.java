package org.luisbaquiax.jwtcaseapi.services.auth;


import org.luisbaquiax.jwtcaseapi.models.RefreshTokens;

public interface RefreshTokenService {
    RefreshTokens guardarTokenAutenticacion(RefreshTokens refreshTokens);

    RefreshTokens validarToken(String usuario, String token);
}
