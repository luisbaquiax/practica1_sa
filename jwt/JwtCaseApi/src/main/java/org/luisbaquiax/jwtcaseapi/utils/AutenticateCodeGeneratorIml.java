package org.luisbaquiax.jwtcaseapi.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AutenticateCodeGeneratorIml implements AutenticateCodeGenerator {
    
    @Override
    public String generate() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }


}
