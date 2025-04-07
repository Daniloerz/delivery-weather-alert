package com.meli.delivery_weather_alert.infraestructure.out.persistence.repository;

import com.meli.delivery_weather_alert.infraestructure.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}