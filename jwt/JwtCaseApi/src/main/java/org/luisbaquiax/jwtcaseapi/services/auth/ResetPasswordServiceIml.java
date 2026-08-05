package org.luisbaquiax.jwtcaseapi.services.auth;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.dtos.ChangePasswordRequest;
import org.luisbaquiax.jwtcaseapi.dtos.ConfirmResetPasswordRequest;
import org.luisbaquiax.jwtcaseapi.dtos.MessageSuccess;
import org.luisbaquiax.jwtcaseapi.exception.IncorrectCredentialsException;
import org.luisbaquiax.jwtcaseapi.exception.NotFoundException;
import org.luisbaquiax.jwtcaseapi.models.PasswordResetToken;
import org.luisbaquiax.jwtcaseapi.repositories.PasswordResetTokenRepository;
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

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final AutenticateCodeGeneratorIml codeGenerator;
    private final PasswordResetTokenRepository resetTokenRepository;
    private final RefreshTokensRepository refreshTokensRepository;
    private final Encriptation encriptation;

    @Transactional
    @Override
    public MessageSuccess resetPassword(String email) {
        usuarioRepository.findByEmail(email).ifPresent(user -> {
            String rawToken = codeGenerator.generateToken();
            String tokenHash = encriptation.hashToken(rawToken);

            resetTokenRepository.save(new PasswordResetToken(
                    null, user, tokenHash,
                    LocalDateTime.now().plusMinutes(15), false, LocalDateTime.now()));

            String resetLink = "http://localhost:4200/reset-password?token=" + rawToken;
            String html = "<p>Solicitaste cambiar tu contraseña</p>"
                    + "<a href=\"" + resetLink + "\">Cambiar contraseña</a>"
                    + "<p>Este enlace expira en 15 minutos.</p>";
            emailService.sendEmail(user.getEmail(), "Restablecer contraseña", html);
        });

        return new MessageSuccess("Si el correo existe, se ha enviado un enlace para restablecer tu contraseña.");
    }

    @Transactional
    @Override
    public MessageSuccess confirmResetPassword(ConfirmResetPasswordRequest request) {
        String tokenHash = encriptation.hashToken(request.token());

        var tokenEntity = resetTokenRepository.findByTokenHash(tokenHash);

        if(tokenEntity == null) {
            throw new NotFoundException("El enlace es inválido o ya expiró.");
        }

        if (tokenEntity.isUsado() || tokenEntity.getExpiraEn().isBefore(LocalDateTime.now()))
            throw new NotFoundException("El enlace es inválido o ya expiró.");

        var user = tokenEntity.getUsuario();
        user.setPasswordHash(encriptation.encriptPassword(request.newPassword()));
        usuarioRepository.save(user);

        tokenEntity.setUsado(true);
        resetTokenRepository.save(tokenEntity);

        refreshTokensRepository.revocarTodosPorUsuario(user.getIdUsuario());

        return new MessageSuccess("Contraseña restablecida exitosamente.");
    }

    @Transactional
    @Override
    public MessageSuccess changePassword(Long idUsuario, ChangePasswordRequest request) {
        var user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado."));

        if (!encriptation.matchesPassword(request.currentPassword(), user.getPasswordHash()))
            throw new IncorrectCredentialsException("La contraseña actual no es correcta.");

        if (encriptation.matchesPassword(request.newPassword(), user.getPasswordHash()))
            throw new IncorrectCredentialsException("La nueva contraseña debe ser distinta a la actual.");

        user.setPasswordHash(encriptation.encriptPassword(request.newPassword()));
        usuarioRepository.save(user);

        refreshTokensRepository.revocarTodosPorUsuario(user.getIdUsuario());

        return new MessageSuccess("Contraseña actualizada exitosamente. Vuelve a iniciar sesión.");
    }
}