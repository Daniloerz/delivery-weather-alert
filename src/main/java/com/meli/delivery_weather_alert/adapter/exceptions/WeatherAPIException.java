package com.meli.delivery_weather_alert.adapter.exceptions;

public class WeatherAPIException extends RuntimeException {
    public WeatherAPIException(String message) {
        super(message);
    }

    public WeatherAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
