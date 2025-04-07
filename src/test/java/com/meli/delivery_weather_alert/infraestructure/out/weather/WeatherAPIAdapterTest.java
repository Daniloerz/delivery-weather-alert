package com.meli.delivery_weather_alert.infraestructure.out.weather;

import com.meli.delivery_weather_alert.infraestructure.out.weather.adapter.WeatherAPIAdapter;
import com.meli.delivery_weather_alert.infraestructure.out.weather.dto.WeatherDto;
import com.meli.delivery_weather_alert.domain.model.WeatherForecast;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WeatherAPIAdapterTest {

    @Mock
    private WeatherAPIClient weatherAPIClient;

    @InjectMocks
    private WeatherAPIAdapter weatherAPIAdapter;

    public WeatherAPIAdapterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getForecastDays_shouldReturnWeatherForecast() {
        String apiKey = "testApiKey";
        String location = "New York";
        int days = 5;

        WeatherDto weatherDto = new WeatherDto("Clear sky", 1234, "USA", "New York", "NY");

        when(weatherAPIClient.getForecastDays(apiKey, location, days)).thenReturn(weatherDto);

        WeatherForecast result = weatherAPIAdapter.getForecastDays(apiKey, location, days);

        assertNotNull(result);
        assertEquals(weatherDto.getCode(), result.getCode());
        assertEquals(weatherDto.getText(), result.getText());
        assertEquals(weatherDto.getCountry(), result.getCountry());
        assertEquals(weatherDto.getName(), result.getLocationName());
        assertEquals(weatherDto.getRegion(), result.getRegion());

        verify(weatherAPIClient, times(1)).getForecastDays(apiKey, location, days);
    }
}
