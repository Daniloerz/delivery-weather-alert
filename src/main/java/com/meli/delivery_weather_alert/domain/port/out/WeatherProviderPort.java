package com.meli.delivery_weather_alert.domain.port.out;

import com.meli.delivery_weather_alert.domain.model.WeatherForecast;

public interface WeatherProviderPort {
    WeatherForecast getForecastDays(String apiKey, String location, int days);
}