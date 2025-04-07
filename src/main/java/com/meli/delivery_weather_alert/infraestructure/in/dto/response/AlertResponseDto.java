package com.meli.delivery_weather_alert.infraestructure.in.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AlertResponseDto {
    @JsonProperty("forecaste_code")
    private final Integer forecastCode;
    @JsonProperty("forecast_descripcion")
    private final String forecastDescription;
    @JsonProperty("buyer_notification")
    private final boolean buyerNotification;
}