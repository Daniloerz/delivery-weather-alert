package com.meli.delivery_weather_alert.infraestructure.out.persistence.repository;

import com.meli.delivery_weather_alert.infraestructure.out.persistence.entity.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConfigurationRepository extends JpaRepository<ConfigurationEntity, Long> {
    ConfigurationEntity findByName(String name);
}
