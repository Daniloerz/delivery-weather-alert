package com.meli.delivery_weather_alert.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AlertResponse {
    @JsonProperty("forecaste_code")
    private final Integer forecastCode;
    @JsonProperty("forecast_descripcion")
    private final String forecastDescription;
    @JsonProperty("buyer_notification")
    private final boolean buyerNotification;
}