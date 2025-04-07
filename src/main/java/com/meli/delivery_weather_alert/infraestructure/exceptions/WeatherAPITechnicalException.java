package com.meli.delivery_weather_alert.infraestructure.exceptions;

public class WeatherAPITechnicalException extends RuntimeException {
    public WeatherAPITechnicalException(String message) {
        super(message);
    }

    public WeatherAPITechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
