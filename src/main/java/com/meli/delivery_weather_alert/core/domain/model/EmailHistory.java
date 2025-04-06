package com.meli.delivery_weather_alert.core.domain.model;

import java.util.Date;

public class EmailHistory {

    private String buyerEmail;
    private Date notificationDate;
    private String country;
    private String locationName;
    private String region;
    private Integer forecastCode;
    private String weatherDescription;

    public EmailHistory(String buyerEmail, Date notificationDate, String country, String locationName, String region, Integer forecastCode, String weatherDescription) {
        this.buyerEmail = buyerEmail;
        this.notificationDate = notificationDate;
        this.country = country;
        this.locationName = locationName;
        this.region = region;
        this.forecastCode = forecastCode;
        this.weatherDescription = weatherDescription;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
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

    public Integer getForecastCode() {
        return forecastCode;
    }

    public void setForecastCode(Integer forecastCode) {
        this.forecastCode = forecastCode;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
}
