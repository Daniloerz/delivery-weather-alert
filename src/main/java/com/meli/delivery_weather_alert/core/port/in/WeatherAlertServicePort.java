package com.meli.delivery_weather_alert.core.port.in;

public interface WeatherAlertServicePort {
    String sendAlert (String latitude, String longitude, String email);
}