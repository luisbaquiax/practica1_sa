package org.luisbaquiax.jwtcaseapi.dtos;

public record UserEmailResponse(
        Long id,
        String username,
        String email,
        String nombres,
        String apellidos
) {
}