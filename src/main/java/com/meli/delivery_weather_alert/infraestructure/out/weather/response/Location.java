package com.meli.delivery_weather_alert.infraestructure.out.weather.response;

import lombok.Data;

@Data
public class Location {
    private final String country;
    private final String name;
    private final String region;
}
