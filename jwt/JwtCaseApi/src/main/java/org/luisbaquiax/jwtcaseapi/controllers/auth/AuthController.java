package org.luisbaquiax.jwtcaseapi.controllers.auth;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.config.security.CustomUserPrincipal;
import org.luisbaquiax.jwtcaseapi.dtos.*;
import org.luisbaquiax.jwtcaseapi.services.auth.LoginService;
import org.luisbaquiax.jwtcaseapi.services.auth.ResetPasswordService;
import org.luisbaquiax.jwtcaseapi.services.auth.VerificarTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final VerificarTokenService verificarTokenService;
    private final ResetPasswordService resetPasswordService;

    @PostMapping("/login")
    public ResponseEntity<AutenticacionResponse> login(@RequestBody AutenticacionRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<AutenticacionResponse> verifyToken(@RequestBody VerifyRequest request) {
        return ResponseEntity.ok(verificarTokenService.verificarToken(request.username(), request.token()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageSuccess> resetPassword(@RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(resetPasswordService.resetPassword(request.email()));
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<MessageSuccess> confirmResetPassword(@RequestBody ConfirmResetPasswordRequest request) {
        return ResponseEntity.ok(resetPasswordService.confirmResetPassword(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<MessageSuccess> changePassword(
            @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(resetPasswordService.changePassword(principal.getIdUsuario(), request));
    }
}

