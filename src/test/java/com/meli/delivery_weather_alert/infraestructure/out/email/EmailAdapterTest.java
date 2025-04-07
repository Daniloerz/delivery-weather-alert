package com.meli.delivery_weather_alert.infraestructure.out.email;

import com.meli.delivery_weather_alert.infraestructure.out.email.adapter.EmailAdapter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class EmailAdapterTest {

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private EmailAdapter emailAdapter;

    public EmailAdapterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail_shouldDelegateToEmailSenderAdapter() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        emailAdapter.sendEmail(to, subject, body);

        verify(emailSender, times(1)).sendEmail(to, subject, body);
    }
}
