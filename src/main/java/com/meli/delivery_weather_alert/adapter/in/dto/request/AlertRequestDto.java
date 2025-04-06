package com.meli.delivery_weather_alert.adapter.in.dto.request;

import lombok.Data;

@Data
public class AlertRequestDto {
    private String email;
    private String latitude;
    private String longitude;
}