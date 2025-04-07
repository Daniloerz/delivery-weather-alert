package com.meli.delivery_weather_alert.infraestructure.out.persistence.repository;

import com.meli.delivery_weather_alert.infraestructure.out.persistence.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmailHistoryRespository extends JpaRepository<EmailHistoryEntity, Long> {
    List<EmailHistoryEntity> findByBuyerEmail (String email);
}
