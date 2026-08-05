package org.luisbaquiax.jwtcaseapi.dtos;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword) {}
