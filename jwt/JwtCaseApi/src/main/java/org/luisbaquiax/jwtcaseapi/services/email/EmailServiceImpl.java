package org.luisbaquiax.jwtcaseapi.services.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.exception.emailexception.EmailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements  EmailService {

    private final JavaMailSender mailSender;

    @Override
    public String sendEmail(String to, String subject, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(mimeMessage);
            return "Email sent successfully";
        } catch (Exception e) {
            throw new EmailException("Failed to send email");
        }
    }

}
