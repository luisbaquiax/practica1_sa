package org.luisbaquiax.jwtcaseapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateProfileRequest(
        @NotBlank(message = "Nombre de usuario obligatorio")
        String username,
        @NotBlank(message = "Correo obligatorio.")
        @Email(message = "Por favor ingresa un correo válido.")
        String email,
        @NotBlank(message = "Nombres obligatorios.")
        @Size(max = 80, message = "Los nombres no pueden exceder los 80 caracteres.")
        String nombres,
        @NotBlank(message = "Apellidos obligatorios.")
        @Size(max = 80, message = "Los apellidos no pueden exceder los 80 caracteres.")
        String apellidos,
        @NotBlank(message = "Telefono obligatorio.")
        String telefono,
        LocalDate fechaNacimiento,
        boolean dobleFactorAuth
) {
}
