package com.meli.delivery_weather_alert.core.domain.model;

public class WeatherForecast {
    private Integer code;
    private String text;

    public WeatherForecast(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}