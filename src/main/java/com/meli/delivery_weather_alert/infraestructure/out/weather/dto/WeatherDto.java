package com.meli.delivery_weather_alert.infraestructure.out.weather.dto;

import lombok.Data;

@Data
public class WeatherDto {
    private final String text;
    private final int code;
    private final String country;
    private final String name;
    private final String region;
}
