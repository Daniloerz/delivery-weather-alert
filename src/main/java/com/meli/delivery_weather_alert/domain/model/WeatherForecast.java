package com.meli.delivery_weather_alert.domain.model;

public class WeatherForecast {
    private Integer code;
    private String text;
    private String country;
    private String locationName;
    private String region;

    public WeatherForecast(Integer code, String text, String country, String locationName, String region) {
        this.code = code;
        this.text = text;
        this.country = country;
        this.locationName = locationName;
        this.region = region;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}