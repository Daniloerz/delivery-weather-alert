package com.meli.delivery_weather_alert.auth.config;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class TokenJwtConfig {
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("mi-clave-secreta-meli-256-bits-123456789".getBytes());
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final long EXPIRATION_TIME = 3600000L;

    private TokenJwtConfig() {}
}
