package com.meli.delivery_weather_alert.infraestructure.exceptions;

public class EmailException extends RuntimeException {
    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}