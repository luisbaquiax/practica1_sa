package org.luisbaquiax.jwtcaseapi.services.auth;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.dtos.ConfirmResetPasswordRequest;
import org.luisbaquiax.jwtcaseapi.dtos.MessageSuccess;
import org.luisbaquiax.jwtcaseapi.exception.NotFoundException;
import org.luisbaquiax.jwtcaseapi.models.RefreshTokens;
import org.luisbaquiax.jwtcaseapi.repositories.RefreshTokensRepository;
import org.luisbaquiax.jwtcaseapi.repositories.UsuarioRepository;
import org.luisbaquiax.jwtcaseapi.services.adminusers.UserService;
import org.luisbaquiax.jwtcaseapi.services.email.EmailService;
import org.luisbaquiax.jwtcaseapi.utils.AutenticateCodeGeneratorIml;
import org.luisbaquiax.jwtcaseapi.utils.Encriptation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceIml implements ResetPasswordService {

    private final UserService userService;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final AutenticateCodeGeneratorIml codeGenerator;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokensRepository refreshTokensRepository;
    private final Encriptation encriptation;

    @Transactional
    @Override
    public MessageSuccess resetPassword(String email) {
        var user = userService.findByEmail(email);
        String token = codeGenerator.generateToken();
        refreshTokenService.guardarTokenAutenticacion(
                new RefreshTokens(null, user, token, LocalDateTime.now().plusMinutes(5), false, LocalDateTime.now()));

        String resetLink = "http://localhost:5173/reset-password?token=" + token;

        String html = "<p>Solicitaste cambiar tu contraseña</p>"
                + "<p>Haz clic en el siguiente enlace:</p>"
                + "<a href=\"" + resetLink + "\">Cambiar contraseña</a>"
                + "<p>Este enlace expira en 15 minutos.</p>";

        emailService.sendEmail(user.getEmail(), "Restablecer contraseña", html);

        return new MessageSuccess("Se ha enviado un correo para restablecer tu contraseña. Por favor, revisa tu bandeja de entrada.");
    }

    @Transactional
    @Override
    public MessageSuccess confirmResetPassword(ConfirmResetPasswordRequest request) {
        var tokenOpt = refreshTokensRepository.findByToken(request.token())
                .orElseThrow(() -> new NotFoundException("No se pudo recuperar la contraseña, lo sentimos, intente más tarde."));

        if (tokenOpt.isRevocado() || tokenOpt.getExpiraEn().isBefore(LocalDateTime.now()))
            throw new NotFoundException("El enlace para restablecer la contraseña ha expirado o ya ha sido utilizado.");

        var user = tokenOpt.getUsuario();
        user.setPasswordHash(encriptation.encriptPassword(request.newPassword()));
        usuarioRepository.save(user);

        refreshTokensRepository.delete(tokenOpt);

        return new MessageSuccess("Contraseña restablecida exitosamente.");
    }
}
