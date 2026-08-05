package org.luisbaquiax.jwtcaseapi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encriptation {
    private PasswordEncoder passwordEncoder;

    public Encriptation(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encriptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
