package com.meli.delivery_weather_alert.infraestructure.out.weather.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {
    @JsonProperty("forecastday")
    private List<ForecastDay> forecastday;
}