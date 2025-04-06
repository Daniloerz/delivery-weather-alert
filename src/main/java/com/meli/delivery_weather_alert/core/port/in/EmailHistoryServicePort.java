package com.meli.delivery_weather_alert.core.port.in;

import com.meli.delivery_weather_alert.adapter.in.dto.response.EmailHistoryResponseDto;
import com.meli.delivery_weather_alert.core.domain.model.EmailHistory;

import java.util.List;

public interface EmailHistoryServicePort {
    List<EmailHistoryResponseDto> getBuyerNotificationHistory(String email);
    void saveBuyerEmailNotification (EmailHistory emailHistory);

}
