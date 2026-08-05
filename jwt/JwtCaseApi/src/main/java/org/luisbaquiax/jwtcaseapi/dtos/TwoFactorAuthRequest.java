package org.luisbaquiax.jwtcaseapi.dtos;

import jakarta.validation.constraints.NotNull;

public record TwoFactorAuthRequest(
        @NotNull(message = "El campo 'activar' es obligatorio")
        boolean activar
) {
}
