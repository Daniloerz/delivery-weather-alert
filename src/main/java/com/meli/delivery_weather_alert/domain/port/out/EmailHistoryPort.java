package com.meli.delivery_weather_alert.domain.port.out;

import com.meli.delivery_weather_alert.domain.model.EmailHistory;

import java.util.List;

public interface EmailHistoryPort {

    List<EmailHistory> getBuyerNotificationHistory(String email);
    void saveBuyerEmailNotification (EmailHistory emailHistory);
}
