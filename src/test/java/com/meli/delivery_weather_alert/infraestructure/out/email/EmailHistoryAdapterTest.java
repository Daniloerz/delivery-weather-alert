package com.meli.delivery_weather_alert.infraestructure.out.email;

import com.meli.delivery_weather_alert.infraestructure.out.persistence.adapter.EmailHistoryAdapter;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.entity.EmailHistoryEntity;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.repository.IEmailHistoryRespository;
import com.meli.delivery_weather_alert.domain.model.EmailHistory;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailHistoryAdapterTest {

    @Mock
    private IEmailHistoryRespository emailHistoryRespository;

    @InjectMocks
    private EmailHistoryAdapter emailHistoryAdapter;

    public EmailHistoryAdapterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBuyerNotificationHistory_shouldReturnMappedEmailHistory() {
        String email = "buyer@example.com";
        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity(
                email,
                new Date(),
                "USA",
                "New York",
                "NY",
                123,
                "Clear sky"
        );

        when(emailHistoryRespository.findByBuyerEmail(anyString())).thenReturn(Arrays.asList(emailHistoryEntity));

        List<EmailHistory> emailHistoryList = emailHistoryAdapter.getBuyerNotificationHistory(email);

        assertNotNull(emailHistoryList);
        assertEquals(1, emailHistoryList.size());
        EmailHistory emailHistory = emailHistoryList.get(0);

        assertEquals(email, emailHistory.getBuyerEmail());
        assertEquals(emailHistoryEntity.getNotificationDate(), emailHistory.getNotificationDate());
        assertEquals(emailHistoryEntity.getCountry(), emailHistory.getCountry());
        assertEquals(emailHistoryEntity.getLocationName(), emailHistory.getLocationName());
        assertEquals(emailHistoryEntity.getRegion(), emailHistory.getRegion());
        assertEquals(emailHistoryEntity.getForecastCode(), emailHistory.getForecastCode());
        assertEquals(emailHistoryEntity.getWeatherDescription(), emailHistory.getWeatherDescription());
    }

    @Test
    void saveBuyerEmailNotification_shouldCallRepositorySave() {
        EmailHistory emailHistory = new EmailHistory(
                "buyer@example.com",
                new Date(),
                "USA",
                "New York",
                "NY",
                123,
                "Clear sky"
        );

        emailHistoryAdapter.saveBuyerEmailNotification(emailHistory);

        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity(
                emailHistory.getBuyerEmail(),
                emailHistory.getNotificationDate(),
                emailHistory.getCountry(),
                emailHistory.getLocationName(),
                emailHistory.getRegion(),
                emailHistory.getForecastCode(),
                emailHistory.getWeatherDescription()
        );

        verify(emailHistoryRespository, times(1)).save(any(EmailHistoryEntity.class));
    }
}
