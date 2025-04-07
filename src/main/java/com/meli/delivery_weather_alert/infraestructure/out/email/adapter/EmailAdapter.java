package com.meli.delivery_weather_alert.infraestructure.out.email.adapter;

import com.meli.delivery_weather_alert.domain.port.out.EmailSenderPort;
import com.meli.delivery_weather_alert.infraestructure.out.email.EmailSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailAdapter implements EmailSenderPort {

    private final EmailSender emailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        emailSender.sendEmail(to, subject, body);
    }
}