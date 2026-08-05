package org.luisbaquiax.jwtcaseapi.dtos;

import jakarta.validation.constraints.NotBlank;

public record ConfirmResetPasswordRequest(
        @NotBlank(message = "El token es obligatorio")
        String token,
        @NotBlank(message = "La nueva contraseña es obligatoria")
        String newPassword
) {
}
