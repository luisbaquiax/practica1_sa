package org.luisbaquiax.jwtcaseapi.services.email;

public interface EmailService {
    String sendEmail(String to, String subject, String text);
}
