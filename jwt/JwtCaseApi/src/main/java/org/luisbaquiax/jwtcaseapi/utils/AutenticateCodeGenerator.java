package org.luisbaquiax.jwtcaseapi.utils;

import org.springframework.stereotype.Component;

@Component
public interface AutenticateCodeGenerator {
    public String generate();

    String generateToken();
}
