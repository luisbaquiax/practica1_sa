package org.luisbaquiax.jwtcaseapi.dtos;

public record VerifyRequest(
        String username,
        String token
) {
}
