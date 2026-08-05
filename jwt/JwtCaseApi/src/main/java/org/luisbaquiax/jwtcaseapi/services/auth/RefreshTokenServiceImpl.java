package org.luisbaquiax.jwtcaseapi.services.auth;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.exception.NotFoundException;
import org.luisbaquiax.jwtcaseapi.models.RefreshTokens;
import org.luisbaquiax.jwtcaseapi.repositories.RefreshTokensRepository;
import org.luisbaquiax.jwtcaseapi.utils.Encriptation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final Encriptation encriptation;

    private final RefreshTokensRepository refreshTokenRepository;

    @Override
    public RefreshTokens guardarTokenAutenticacion(RefreshTokens refreshTokens) {
        return refreshTokenRepository.save(refreshTokens);
    }

    @Override
    public RefreshTokens validarToken(String username, String token) {
        RefreshTokens refreshToken = refreshTokenRepository
                .findByUsuarioUsernameAndToken(username, token)
                .orElseThrow(() -> new NotFoundException("Token de autenticación inválido o ya utilizado."));

        refreshToken.setRevocado(true);
        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }
}
