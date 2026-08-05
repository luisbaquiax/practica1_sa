package org.luisbaquiax.jwtcaseapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UsuarioComunRequest(
        @NotBlank(message = "Nombre de usuario obligatorio")
        String username,
        @NotBlank(message = "Correo obligatorio.")
        @Email(message = "Por favor ingresa un correo válido.")
        String email,
        @NotBlank(message = "Contraseña obligatoria.")
        String password,
        @NotBlank(message = "Nombres obligatorios.")
        String nombres,
        @NotBlank(message = "Apellidos obligatorios.")
        String apellidos,
        @NotBlank(message = "Telefono obligatorio.")
        String telefono,
        LocalDate fechaNacimiento
) {
}
