package com.meli.delivery_weather_alert.core.domain.exception;

public class AuthenticationRequestParseException extends RuntimeException {
    public AuthenticationRequestParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
