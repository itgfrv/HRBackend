package com.gafarov.bastion.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Getter
public class MailConfig {
    @Value("${mail.host}")
    private String host = "localhost";

    @Value("${mail.port}")
    private int port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.auth}")
    private Boolean smtpAuth;

    @Value("${mail.tls}")
    private Boolean tls;

    @Value("${mail.protocol}")
    private String protocol;

    @Value("${mail.hr}")
    private String hr;

    @Value("${mail.userLink}")
    private String userLink;

    @Value("${mail.resetLink}")
    private String resetLink;

    public String getHrMail() {
        return hr;
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);

        Properties sendProperties = new Properties(4);
        sendProperties.setProperty("mail.smtp.auth", smtpAuth.toString());
        sendProperties.setProperty("mail.smtp.starttls.enable", tls.toString());
        sendProperties.setProperty("mail.transport.protocol", protocol);
        sendProperties.setProperty("mail.smtp.ssl.trust", "*");
        sender.setJavaMailProperties(sendProperties);
        return sender;
    }
}