package org.luisbaquiax.jwtcaseapi.services.auth;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.config.security.CustomUserDetailsService;
import org.luisbaquiax.jwtcaseapi.config.security.JwtService;
import org.luisbaquiax.jwtcaseapi.dtos.AutenticacionResponse;
import org.luisbaquiax.jwtcaseapi.exception.BussnessException;
import org.luisbaquiax.jwtcaseapi.models.RefreshTokens;
import org.luisbaquiax.jwtcaseapi.models.Usuario;
import org.luisbaquiax.jwtcaseapi.repositories.RefreshTokensRepository;
import org.luisbaquiax.jwtcaseapi.services.adminusers.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificarTokenServiceImpl implements VerificarTokenService {

    private final RefreshTokensRepository refreshTokensRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;

    @Transactional
    @Override
    public AutenticacionResponse verificarToken(String username, String token) {
        RefreshTokens refreshTokens = refreshTokenService.validarToken(username, token);

        if(refreshTokens.getExpiraEn().isBefore(LocalDateTime.now()))
            throw new BussnessException("El token ha expirado, por favor inicie sesión nuevamente.");

        Usuario usuario = userService.findByUsername(username);

        String jwtToken = jwtService.generateToken(customUserDetailsService.loadUserByUsername(refreshTokens.getUsuario().getUsername()), usuario.getIdUsuario());

        //eliminar el token de autenticación para que no pueda ser reutilizado
        refreshTokensRepository.delete(refreshTokens);

        return new AutenticacionResponse(
                jwtToken,
                usuario.getUsername(),
                usuario.getIdUsuario(),
                usuario.isDobleFactorAuth(),
                usuario.isPrimeraVez(),
                usuario.getRoles().stream().map(usuarioRol -> usuarioRol.getRol().getNombre()).toList()
        );
    }
}
