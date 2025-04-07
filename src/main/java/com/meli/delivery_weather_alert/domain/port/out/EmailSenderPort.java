package com.meli.delivery_weather_alert.domain.port.out;

public interface EmailSenderPort {
    void sendEmail(String to, String subject, String body);
}
