package com.meli.delivery_weather_alert.core.port.in;

import com.meli.delivery_weather_alert.core.domain.model.Configuration;

public interface ConfigurationServicePort {
    Configuration createConfiguration(String name, String value);
    Configuration getConfiguration(String id);
}