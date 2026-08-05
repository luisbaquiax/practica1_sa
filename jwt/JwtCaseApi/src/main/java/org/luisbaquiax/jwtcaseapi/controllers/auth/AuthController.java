package org.luisbaquiax.jwtcaseapi.controllers.auth;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.dtos.AutenticacionRequest;
import org.luisbaquiax.jwtcaseapi.dtos.AutenticacionResponse;
import org.luisbaquiax.jwtcaseapi.dtos.MessageSuccess;
import org.luisbaquiax.jwtcaseapi.dtos.VerifyRequest;
import org.luisbaquiax.jwtcaseapi.services.auth.LoginService;
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

    @PostMapping("/login")
    public ResponseEntity<AutenticacionResponse> login(@RequestBody AutenticacionRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<AutenticacionResponse> verifyToken(@RequestBody VerifyRequest request) {
        return ResponseEntity.ok(verificarTokenService.verificarToken(request.username(), request.token()));
    }

    @PostMapping("/reset-password/{email}")
    public ResponseEntity<MessageSuccess> resetPassword(@PathVariable String email) {
        return ResponseEntity.ok(resetPasswordService.resetPassword(email));
    }
}
