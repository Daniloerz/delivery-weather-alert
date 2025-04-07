package com.meli.delivery_weather_alert.infraestructure.out.persistence.adapter;

import com.meli.delivery_weather_alert.infraestructure.out.persistence.repository.IConfigurationRepository;
import com.meli.delivery_weather_alert.domain.model.Configuration;
import com.meli.delivery_weather_alert.domain.port.out.ConfigurationRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ConfigurationRepositoryAdapter implements ConfigurationRepositoryPort {

    private final IConfigurationRepository configurationRepository;

    @Override
    public Configuration findConfigurationByName(String name) {
        return Optional.ofNullable(configurationRepository.findByName(name))
                .map(configEntity -> new Configuration(configEntity.getName(), configEntity.getValue()))
                .orElse(null);
    }
}
