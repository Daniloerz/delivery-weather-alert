package com.meli.delivery_weather_alert.infraestructure.in.dto.response;

import lombok.Data;

import java.util.Date;
@Data
public class EmailHistoryResponseDto {
    private final String buyerEmail;
    private final Date notificationDate;
    private final String country;
    private final String locationName;
    private final String region;
    private final Integer forecastCode;
    private final String weatherDescription;
}
