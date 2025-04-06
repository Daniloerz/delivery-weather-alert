package com.meli.delivery_weather_alert.core.service;

import com.meli.delivery_weather_alert.adapter.in.dto.response.EmailHistoryResponseDto;
import com.meli.delivery_weather_alert.core.domain.model.EmailHistory;
import com.meli.delivery_weather_alert.core.port.in.EmailHistoryServicePort;
import com.meli.delivery_weather_alert.core.port.out.EmailHistoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EmailHistoryService implements EmailHistoryServicePort {

    private final EmailHistoryPort emailHistoryPort;

    @Override
    public List<EmailHistoryResponseDto> getBuyerNotificationHistory(String email) {
        return emailHistoryPort.getBuyerNotificationHistory(email).stream().map(emailHistoryEntity -> new EmailHistoryResponseDto(
                emailHistoryEntity.getBuyerEmail(),
                emailHistoryEntity.getNotificationDate(),
                emailHistoryEntity.getCountry(),
                emailHistoryEntity.getLocationName(),
                emailHistoryEntity.getRegion(),
                emailHistoryEntity.getForecastCode(),
                emailHistoryEntity.getWeatherDescription()
        )).collect(Collectors.toList());
    }

    @Override
    public void saveBuyerEmailNotification(EmailHistory emailHistory) {
        emailHistoryPort.saveBuyerEmailNotification(emailHistory);
    }
}
