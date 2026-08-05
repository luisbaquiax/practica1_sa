package org.luisbaquiax.jwtcaseapi.services.auth;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.config.security.CustomUserDetailsService;
import org.luisbaquiax.jwtcaseapi.config.security.JwtService;
import org.luisbaquiax.jwtcaseapi.dtos.AutenticacionRequest;
import org.luisbaquiax.jwtcaseapi.dtos.AutenticacionResponse;
import org.luisbaquiax.jwtcaseapi.exception.IncorrectCredentialsException;
import org.luisbaquiax.jwtcaseapi.models.RefreshTokens;
import org.luisbaquiax.jwtcaseapi.models.Usuario;
import org.luisbaquiax.jwtcaseapi.repositories.UsuarioRepository;
import org.luisbaquiax.jwtcaseapi.services.email.NotificactionTokenAuthService;
import org.luisbaquiax.jwtcaseapi.utils.Encriptation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UsuarioRepository usuarioRepository;
    private final RefreshTokenService refreshTokenService;
    private final Encriptation encriptation;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final NotificactionTokenAuthService notificationTokenAuthService;

    @Transactional
    @Override
    public AutenticacionResponse login(AutenticacionRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new IncorrectCredentialsException("Credenciales incorrectas."));

        if (!encriptation.matchesPassword(request.password(), usuario.getPasswordHash()))
            throw new IncorrectCredentialsException("Credenciales incorrectas.");

        if (usuario.isDobleFactorAuth()) {
            String tokenEnviado = notificationTokenAuthService.sendTokenAuth(usuario.getEmail());

            RefreshTokens refreshToken = new RefreshTokens();
            refreshToken.setUsuario(usuario);
            refreshToken.setToken(tokenEnviado);
            refreshToken.setExpiraEn(LocalDateTime.now().plusMinutes(5));
            refreshToken.setCreatedAt(LocalDateTime.now());
            refreshTokenService.guardarTokenAutenticacion(refreshToken);

            return new AutenticacionResponse(
                    null,
                    usuario.getUsername(),
                    null,
                    usuario.isDobleFactorAuth(),
                    usuario.isPrimeraVez(),
                    null
            );
        }
        String jwtToken = jwtService.generateToken(
                customUserDetailsService.loadUserByUsername(usuario.getUsername()),
                usuario.getIdUsuario()
        );

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
