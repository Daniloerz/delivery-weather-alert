package com.meli.delivery_weather_alert.core.service;

import com.meli.delivery_weather_alert.adapter.in.dto.response.EmailHistoryResponseDto;
import com.meli.delivery_weather_alert.core.domain.model.EmailHistory;
import com.meli.delivery_weather_alert.core.port.out.EmailHistoryPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailHistoryServiceTest {

    @Mock
    private EmailHistoryPort emailHistoryPort;

    @InjectMocks
    private EmailHistoryService emailHistoryService;

    public EmailHistoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBuyerNotificationHistory_shouldReturnCorrectResponseDto() {
        String email = "buyer@example.com";
        EmailHistory emailHistory = new EmailHistory(
                email,
                new Date(),
                "USA",
                "New York",
                "NY",
                123,
                "Clear sky"
        );

        when(emailHistoryPort.getBuyerNotificationHistory(email))
                .thenReturn(Arrays.asList(emailHistory));

        List<EmailHistoryResponseDto> response = emailHistoryService.getBuyerNotificationHistory(email);

        assertNotNull(response);
        assertEquals(1, response.size());
        EmailHistoryResponseDto dto = response.get(0);
        assertEquals(email, dto.getBuyerEmail());
        assertEquals(emailHistory.getNotificationDate(), dto.getNotificationDate());
        assertEquals(emailHistory.getCountry(), dto.getCountry());
        assertEquals(emailHistory.getLocationName(), dto.getLocationName());
        assertEquals(emailHistory.getRegion(), dto.getRegion());
        assertEquals(emailHistory.getForecastCode(), dto.getForecastCode());
        assertEquals(emailHistory.getWeatherDescription(), dto.getWeatherDescription());
    }

    @Test
    void saveBuyerEmailNotification_shouldCallSaveMethod() {
        EmailHistory emailHistory = new EmailHistory(
                "buyer@example.com",
                new Date(),
                "USA",
                "New York",
                "NY",
                123,
                "Clear sky"
        );

        emailHistoryService.saveBuyerEmailNotification(emailHistory);

        verify(emailHistoryPort, times(1)).saveBuyerEmailNotification(emailHistory);
    }
}
