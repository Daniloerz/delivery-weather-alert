package com.meli.delivery_weather_alert.infraestructure.out.weather.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Day {
    @JsonProperty("maxtemp_c")
    private double maxtempC;

    @JsonProperty("condition")
    private Condition condition;

}