package com.meli.delivery_weather_alert.infraestructure.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "emails_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buyer_email")
    private String buyerEmail;

    @Column(name = "notification_date")
    private Date notificationDate;

    @Column(name = "country")
    private String country;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "region")
    private String region;

    @Column(name = "forecast_code")
    private Integer forecastCode;

    @Column(name = "weather_description")
    private String weatherDescription;

    public EmailHistoryEntity(String buyerEmail, Date notificationDate, String country, String locationName, String region, Integer forecastCode, String weatherDescription) {
        this.buyerEmail = buyerEmail;
        this.notificationDate = notificationDate;
        this.country = country;
        this.locationName = locationName;
        this.region = region;
        this.forecastCode = forecastCode;
        this.weatherDescription = weatherDescription;
    }
}
