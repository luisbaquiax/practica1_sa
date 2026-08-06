package org.luisbaquiax.jwtcaseapi.services.email;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.utils.AutenticateCodeGeneratorIml;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificactionTokenAuthServiceImpl implements  NotificactionTokenAuthService {
    private final EmailService emailService;
    private final AutenticateCodeGeneratorIml generator;

    public String sendTokenAuth(String to) {
        String token = generator.generate();
        String subject = "Token de autenticación";
        String text = "<p>Su token de autenticación es: <b>" + token + "</b></p>";
        emailService.sendEmail(to, subject, text);
        return token;
    }
}
