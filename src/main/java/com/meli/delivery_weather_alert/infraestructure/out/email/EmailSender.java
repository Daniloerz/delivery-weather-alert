package com.meli.delivery_weather_alert.infraestructure.out.email;

import com.meli.delivery_weather_alert.infraestructure.exceptions.ConfigurationException;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.entity.ConfigurationEntity;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.repository.IConfigurationRepository;
import com.meli.delivery_weather_alert.infraestructure.exceptions.EmailException;
import com.meli.delivery_weather_alert.shared.utils.Utils;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class EmailSender {

    @Value("${email.smtp.host}")
    private String smtpHost;

    @Value("${email.smtp.port}")
    private int smtpPort;

    @Value("${email.from}")
    private String fromEmail;

    @Value("${email.tls.enabled:true}")
    private boolean tlsEnabled;

    @Value("${crypto.internal_key}")
    private String internalKey;

    private final IConfigurationRepository configurationRepository;

    public void sendEmail(String to, String subject, String body) {
        String username = Optional.ofNullable(configurationRepository.findByName("user_email")).map(ConfigurationEntity::getValue)
                .orElseThrow(() -> new ConfigurationException("Email not configured"));
        String emailKey = Optional.ofNullable(configurationRepository.findByName("google_key")).map(ConfigurationEntity::getValue)
                .map(field -> Utils.decrypt(field,internalKey))
                .orElseThrow(() -> new ConfigurationException("email_key not configured"));
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", String.valueOf(tlsEnabled));
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, emailKey);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new EmailException("Error sending email to " + to, e);
        }
    }
}