package com.meli.delivery_weather_alert.auth.exceptions;

public class AuthenticationRequestParseException extends RuntimeException {
    public AuthenticationRequestParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
