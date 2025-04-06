package com.meli.delivery_weather_alert.domain.port.in;

import com.meli.delivery_weather_alert.infraestructure.in.dto.response.EmailHistoryResponseDto;
import com.meli.delivery_weather_alert.domain.model.EmailHistory;

import java.util.List;

public interface EmailHistoryServicePort {
    List<EmailHistoryResponseDto> getBuyerNotificationHistory(String email);
    void saveBuyerEmailNotification (EmailHistory emailHistory);

}
