package org.luisbaquiax.jwtcaseapi.dtos;

public record AutenticacionRequest (
        String username,
        String password
) {

}
