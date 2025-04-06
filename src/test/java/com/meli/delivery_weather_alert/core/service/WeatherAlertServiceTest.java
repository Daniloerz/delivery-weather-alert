package com.meli.delivery_weather_alert.core.service;

import com.meli.delivery_weather_alert.adapter.in.dto.response.AlertResponseDto;
import com.meli.delivery_weather_alert.core.domain.model.Configuration;
import com.meli.delivery_weather_alert.core.domain.model.EmailHistory;
import com.meli.delivery_weather_alert.core.domain.model.WeatherForecast;
import com.meli.delivery_weather_alert.core.port.in.EmailHistoryServicePort;
import com.meli.delivery_weather_alert.core.port.out.EmailSenderPort;
import com.meli.delivery_weather_alert.core.port.out.RepositoryPort;
import com.meli.delivery_weather_alert.core.port.out.WeatherProviderPort;
import com.meli.delivery_weather_alert.shared.utils.Utils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherAlertServiceTest {

    @Mock
    private WeatherProviderPort weatherProviderPort;

    @Mock
    private RepositoryPort repositoryPort;

    @Mock
    private EmailSenderPort emailSenderPort;

    @Mock
    private EmailHistoryServicePort emailHistoryServicePort;

    @InjectMocks
    private WeatherAlertService weatherAlertService;

    public WeatherAlertServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendAlert_shouldReturnAlertResponseDtoWithTrueIfAlertIsSent() {
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
            mockedStatic.when(() -> Utils.decrypt(anyString(), anyString())).thenReturn("");
            String latitude = "40.7128";
            String longitude = "-74.0060";
            String email = "buyer@example.com";

            String location = String.join(",", latitude, longitude);
            String apiKey = "mocked-api-key";
            WeatherForecast weatherForecast = new WeatherForecast(1186, "Clear", "USA", "New York", "NY");

            when(repositoryPort.findConfigurationByName("weather_api_key"))
                    .thenReturn(new Configuration("weather_api_key", apiKey));

            when(weatherProviderPort.getForecastDays(anyString(), anyString(),anyInt())).thenReturn(weatherForecast);

            AlertResponseDto response = weatherAlertService.sendAlert(latitude, longitude, email);

            assertNotNull(response);
            assertTrue(response.isBuyerNotification());
            assertEquals(weatherForecast.getCode(), response.getForecastCode());
            assertEquals(weatherForecast.getText(), response.getForecastDescription());

            verify(emailSenderPort, times(1)).sendEmail(eq(email), anyString(), anyString());
            verify(emailHistoryServicePort, times(1)).saveBuyerEmailNotification(any(EmailHistory.class));
        }
    }

    @Test
    void sendAlert_shouldReturnAlertResponseDtoWithFalseIfNoAlertIsSent() {
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
            mockedStatic.when(() -> Utils.decrypt(anyString(), anyString())).thenReturn("");
            String latitude = "40.7128";
            String longitude = "-74.0060";
            String email = "buyer@example.com";

            String location = String.join(",", latitude, longitude);
            String apiKey = "mocked-api-key";
            WeatherForecast weatherForecast = new WeatherForecast(1234, "Clear", "USA", "New York", "NY");

            when(repositoryPort.findConfigurationByName("weather_api_key"))
                    .thenReturn(new Configuration("weather_api_key", apiKey));

            when(weatherProviderPort.getForecastDays(anyString(), anyString(),anyInt())).thenReturn(weatherForecast);

            AlertResponseDto response = weatherAlertService.sendAlert(latitude, longitude, email);

            assertNotNull(response);
            assertFalse(response.isBuyerNotification());
            assertEquals(weatherForecast.getCode(), response.getForecastCode());
            assertEquals(weatherForecast.getText(), response.getForecastDescription());

            verify(emailSenderPort, times(0)).sendEmail(anyString(), anyString(), anyString());
            verify(emailHistoryServicePort, times(0)).saveBuyerEmailNotification(any(EmailHistory.class));
        }
    }
}
