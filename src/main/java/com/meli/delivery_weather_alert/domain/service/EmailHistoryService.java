package com.meli.delivery_weather_alert.domain.service;

import com.meli.delivery_weather_alert.infraestructure.in.dto.response.EmailHistoryResponseDto;
import com.meli.delivery_weather_alert.domain.model.EmailHistory;
import com.meli.delivery_weather_alert.domain.port.in.EmailHistoryServicePort;
import com.meli.delivery_weather_alert.domain.port.out.EmailHistoryPort;
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
