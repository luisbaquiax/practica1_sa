package org.luisbaquiax.jwtcaseapi.controllers;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.dtos.*;
import org.luisbaquiax.jwtcaseapi.services.auth.LoginService;
import org.luisbaquiax.jwtcaseapi.services.auth.RefreshTokenService;
import org.luisbaquiax.jwtcaseapi.services.auth.ResetPasswordService;
import org.luisbaquiax.jwtcaseapi.services.auth.VerificarTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final VerificarTokenService verificarTokenService;
    private final ResetPasswordService resetPasswordService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<AutenticacionResponse> login(@RequestBody AutenticacionRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageSuccess> logout(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.substring(7); // quita "Bearer "
        return ResponseEntity.ok(refreshTokenService.logout(jwt));
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

}

