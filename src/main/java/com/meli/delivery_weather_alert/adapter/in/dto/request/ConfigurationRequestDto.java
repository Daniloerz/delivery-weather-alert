package com.meli.delivery_weather_alert.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationRequestDto {
    private String name;
    private String value;
}