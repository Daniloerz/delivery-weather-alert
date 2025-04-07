package com.meli.delivery_weather_alert.infraestructure.out.persistence.adapter;

import com.meli.delivery_weather_alert.infraestructure.out.persistence.entity.EmailHistoryEntity;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.repository.IEmailHistoryRespository;

import com.meli.delivery_weather_alert.domain.model.EmailHistory;
import com.meli.delivery_weather_alert.domain.port.out.EmailHistoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EmailHistoryAdapter implements EmailHistoryPort {

    private final IEmailHistoryRespository emailHistoryRespository;

    @Override
    public List<EmailHistory> getBuyerNotificationHistory(String email) {
        return emailHistoryRespository.findByBuyerEmail(email).stream().map(emailHistoryEntity -> new EmailHistory(
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
        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity(emailHistory.getBuyerEmail(),
                emailHistory.getNotificationDate(), emailHistory.getCountry(), emailHistory.getLocationName(),
                emailHistory.getRegion(), emailHistory.getForecastCode(), emailHistory.getWeatherDescription());
        emailHistoryRespository.save(emailHistoryEntity);
    }
}
