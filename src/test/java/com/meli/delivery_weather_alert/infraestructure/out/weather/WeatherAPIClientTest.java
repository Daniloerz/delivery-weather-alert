package com.meli.delivery_weather_alert.infraestructure.out.weather;

import com.meli.delivery_weather_alert.infraestructure.exceptions.WeatherAPIException;
import com.meli.delivery_weather_alert.infraestructure.out.weather.dto.WeatherDto;
import com.meli.delivery_weather_alert.infraestructure.out.weather.response.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherAPIClientTest {


    private RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    private WeatherAPIClient weatherAPIClient = new WeatherAPIClient(restTemplate,"http://www.url.com");


    @Test
    void getForecastDays_shouldReturnWeatherDto() {
        String apiKey = "testApiKey";
        String location = "New York";
        int days = 5;

        Condition condition = new Condition();
        condition.setText("Clear sky");
        condition.setCode(1234);

        Day day = new Day();
        day.setCondition(condition);

        ForecastDay forecastDay = new ForecastDay();
        forecastDay.setDay(day);

        Location locationResponse = new Location("USA", "New York", "NY");

        Forecast forecast = new Forecast();
        forecast.setForecastday(List.of(forecastDay));

        WeatherAPIResponse weatherAPIResponse = new WeatherAPIResponse();
        weatherAPIResponse.setLocation(locationResponse);
        weatherAPIResponse.setForecast(forecast);

        when(restTemplate.getForObject(anyString(), eq(WeatherAPIResponse.class)))
                .thenReturn(weatherAPIResponse);

        WeatherDto result = weatherAPIClient.getForecastDays(apiKey, location, days);

        assertNotNull(result);
        assertEquals("Clear sky", result.getText());
        assertEquals(1234, result.getCode());
        assertEquals("USA", result.getCountry());
        assertEquals("New York", result.getName());
        assertEquals("NY", result.getRegion());

        verify(restTemplate, times(1)).getForObject(anyString(), eq(WeatherAPIResponse.class));
    }

    @Test
    void getForecastDays_shouldThrowWeatherAPIExceptionIfWeatherNotFound() {
        String apiKey = "testApiKey";
        String location = "New York";
        int days = 5;

        WeatherAPIResponse weatherAPIResponse = new WeatherAPIResponse();
        weatherAPIResponse.setLocation(new Location("USA", "New York", "NY"));
        weatherAPIResponse.setForecast(new Forecast(List.of(new ForecastDay())));

        when(restTemplate.getForObject(anyString(), eq(WeatherAPIResponse.class)))
                .thenReturn(weatherAPIResponse);

        WeatherAPIException thrown = assertThrows(WeatherAPIException.class, () -> {
            weatherAPIClient.getForecastDays(apiKey, location, days);
        });

        assertEquals("No se encontró información de pronóstico", thrown.getMessage());

        verify(restTemplate, times(1)).getForObject(anyString(), eq(WeatherAPIResponse.class));
    }
}
