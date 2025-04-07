package com.meli.delivery_weather_alert.infraestructure.out.weather.adapter;

import com.meli.delivery_weather_alert.infraestructure.out.weather.WeatherAPIClient;
import com.meli.delivery_weather_alert.infraestructure.out.weather.dto.WeatherDto;
import com.meli.delivery_weather_alert.domain.model.WeatherForecast;
import com.meli.delivery_weather_alert.domain.port.out.WeatherProviderPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WeatherAPIAdapter implements WeatherProviderPort {

    private final WeatherAPIClient weatherAPIClient;

    @Override
    @CircuitBreaker(name = "weatherApi", fallbackMethod = "fallbackForecast")
    public WeatherForecast getForecastDays(String apiKey, String location, int days) {
        WeatherDto dto = weatherAPIClient.getForecastDays(apiKey, location, days);
        return new WeatherForecast(dto.getCode(), dto.getText(), dto.getCountry(), dto.getName(), dto.getRegion());
    }

    public WeatherForecast fallbackForecast(String apiKey, String location, int days, Throwable throwable) {
        return new WeatherForecast(
                0,
                "Sin datos disponibles",
                "Desconocido",
                location,
                "Desconocido"
        );
    }
}
