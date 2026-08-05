package org.luisbaquiax.jwtcaseapi.controllers;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.config.security.CustomUserPrincipal;
import org.luisbaquiax.jwtcaseapi.dtos.ChangePasswordRequest;
import org.luisbaquiax.jwtcaseapi.dtos.MessageSuccess;
import org.luisbaquiax.jwtcaseapi.services.auth.ResetPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users/me")
@RequiredArgsConstructor
public class ChangePasswordController {

    private final ResetPasswordService resetPasswordService;

    @PostMapping("/change-password")
    public ResponseEntity<MessageSuccess> changePassword(
            @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(resetPasswordService.changePassword(principal.getIdUsuario(), request));
    }

}
