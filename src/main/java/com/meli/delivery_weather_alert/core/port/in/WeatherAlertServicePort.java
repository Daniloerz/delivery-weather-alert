package com.meli.delivery_weather_alert.core.port.in;

import com.meli.delivery_weather_alert.adapter.in.dto.response.AlertResponseDto;

public interface WeatherAlertServicePort {
    AlertResponseDto sendAlert (String latitude, String longitude, String email);
}