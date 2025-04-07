package com.meli.delivery_weather_alert.infraestructure.out.email;

import com.meli.delivery_weather_alert.infraestructure.exceptions.ConfigurationException;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.entity.ConfigurationEntity;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.repository.IConfigurationRepository;
import com.meli.delivery_weather_alert.shared.utils.Utils;
import jakarta.mail.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailSenderTest {

    @Mock
    private IConfigurationRepository configurationRepository;

    @InjectMocks
    private EmailSender emailSender;

    @Mock
    private ConfigurationEntity configurationEntity;

    public EmailSenderTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field smtpHostField = emailSender.getClass().getDeclaredField("smtpHost");
        smtpHostField.setAccessible(true);
        smtpHostField.set(emailSender, "smtp.example.com");

        Field smtpPortField = emailSender.getClass().getDeclaredField("smtpPort");
        smtpPortField.setAccessible(true);
        smtpPortField.set(emailSender, 587);

        Field fromEmailField = emailSender.getClass().getDeclaredField("fromEmail");
        fromEmailField.setAccessible(true);
        fromEmailField.set(emailSender, "from@example.com");

        Field tlsEnabledField = EmailSender.class.getDeclaredField("tlsEnabled");
        tlsEnabledField.setAccessible(true);
        tlsEnabledField.set(emailSender, true);

        Field internalKeyField = EmailSender.class.getDeclaredField("internalKey");
        internalKeyField.setAccessible(true);
        internalKeyField.set(emailSender, "internal_key");


    }

    @Test
    void sendEmail_shouldThrowConfigurationExceptionIfUserEmailNotConfigured() {
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
            String to = "test@example.com";
            String subject = "Test Subject";
            String body = "Test Body";

            when(configurationRepository.findByName("user_email")).thenReturn(null);

            ConfigurationException thrown = assertThrows(ConfigurationException.class, () -> {
                emailSender.sendEmail(to, subject, body);
            });

            assertEquals("Email not configured", thrown.getMessage());
        }
    }

    @Test
    void sendEmail_shouldThrowConfigurationExceptionIfEmailKeyNotConfigured() {
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {

            String to = "test@example.com";
            String subject = "Test Subject";
            String body = "Test Body";

            when(configurationRepository.findByName("user_email")).thenReturn(configurationEntity);
            when(configurationEntity.getValue()).thenReturn("testUser");

            when(configurationRepository.findByName("google_key")).thenReturn(null);

            ConfigurationException thrown = assertThrows(ConfigurationException.class, () -> {
                emailSender.sendEmail(to, subject, body);
            });

            assertEquals("email_key not configured", thrown.getMessage());
        }
    }

    @Test
    void sendEmail_shouldSendEmailSuccessfully() throws MessagingException {
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class);
        MockedStatic<Transport> mockedTransport = mockStatic(Transport.class)) {

            String to = "test@example.com";
            String subject = "Test Subject";
            String body = "Test Body";

            String username = "testUser";
            String emailKey = "testKey";

            mockedStatic.when(() -> Utils.decrypt(anyString(),anyString())).thenReturn("decrypt");
            mockedTransport.when(() -> Transport.send(any())).thenAnswer(invocation -> {
                return null;
            });
            when(configurationRepository.findByName("user_email")).thenReturn(configurationEntity);
            when(configurationEntity.getValue()).thenReturn(username);
            when(configurationRepository.findByName("google_key")).thenReturn(configurationEntity);
            when(configurationEntity.getValue()).thenReturn(emailKey);

            assertDoesNotThrow(()-> emailSender.sendEmail(to, subject, body));

        }
    }
}
