package com.meli.delivery_weather_alert.adapter.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfigurationResponseDto {
    private Long id;
    private String name;
    private String value;
}