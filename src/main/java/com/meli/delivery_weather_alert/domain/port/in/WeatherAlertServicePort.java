package com.meli.delivery_weather_alert.domain.port.in;

import com.meli.delivery_weather_alert.infraestructure.in.dto.response.AlertResponseDto;

public interface WeatherAlertServicePort {
    AlertResponseDto sendAlert (String latitude, String longitude, String email);
}