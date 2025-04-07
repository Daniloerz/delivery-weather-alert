package com.meli.delivery_weather_alert.domain.port.out;

import com.meli.delivery_weather_alert.domain.model.Configuration;

public interface ConfigurationRepositoryPort {
    Configuration findConfigurationByName(String name);
}