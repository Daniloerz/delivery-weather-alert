package com.meli.delivery_weather_alert.adapter.in.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AlertRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Latitude is required")
    @Size(min = 1, max = 50, message = "Latitude should not be empty")
    private String latitude;

    @NotNull(message = "Longitude is required")
    @Size(min = 1, max = 50, message = "Longitude should not be empty")
    private String longitude;
}