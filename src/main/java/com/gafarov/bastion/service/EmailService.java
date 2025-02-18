package com.gafarov.bastion.service;

import com.gafarov.bastion.config.MailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailConfig config;

    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                                                         MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                                                         StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(variables);
        String htmlContent = templateEngine.process(templateName, context);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(config.getUsername());
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    public void sendApplicationNotification(String userId, String applicantName ) throws MessagingException {
        String resumeLink = config.getUserLink()+userId;
        Map<String, Object> variables = Map.of("resumeLink", resumeLink, "applicantName", applicantName);
        sendHtmlEmail(config.getHrMail(), "Новая заявка на вакансию", "hr-notification", variables);
    }
    public void sendChangePasswordRequest(String userEmail, String resetId) throws MessagingException {
        String resetLink = config.getResetLink()+resetId;
        Map<String, Object> variables = Map.of("resetLink", resetLink);
        sendHtmlEmail(userEmail, "Сброс пароля", "password-reset", variables);
    }

}
