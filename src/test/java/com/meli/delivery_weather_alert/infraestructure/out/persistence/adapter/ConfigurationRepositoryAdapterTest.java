package com.meli.delivery_weather_alert.infraestructure.out.persistence.adapter;

import com.meli.delivery_weather_alert.infraestructure.out.persistence.repository.IConfigurationRepository;
import com.meli.delivery_weather_alert.domain.model.Configuration;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.entity.ConfigurationEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ConfigurationRepositoryAdapterTest {

    @Mock
    private IConfigurationRepository configurationRepository;

    @InjectMocks
    private ConfigurationRepositoryAdapter configurationRepositoryAdapter;

    public ConfigurationRepositoryAdapterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findConfigurationByName_shouldReturnConfigurationIfExists() {
        Long id = 1L;
        String configName = "someConfig";
        String configValue = "configValue";
        ConfigurationEntity configurationEntity = new ConfigurationEntity(id, configName, configValue);

        when(configurationRepository.findByName(configName)).thenReturn(configurationEntity);

        Configuration result = configurationRepositoryAdapter.findConfigurationByName(configName);

        assertNotNull(result);
        assertEquals(configName, result.getName());
        assertEquals(configValue, result.getValue());
    }

    @Test
    void findConfigurationByName_shouldReturnNullIfNotFound() {
        String configName = "nonExistentConfig";

        when(configurationRepository.findByName(configName)).thenReturn(null);
        Configuration result = configurationRepositoryAdapter.findConfigurationByName(configName);
        assertNull(result);
    }

    @Test
    void findConfigurationByName_shouldReturnNullIfRepositoryReturnsNull() {
        String configName = "someConfig";

        when(configurationRepository.findByName(configName)).thenReturn(null);

        Configuration result = configurationRepositoryAdapter.findConfigurationByName(configName);

        assertNull(result);
    }
}
