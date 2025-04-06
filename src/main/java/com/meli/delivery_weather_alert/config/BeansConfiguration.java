package com.meli.delivery_weather_alert.config;

import com.meli.delivery_weather_alert.infraestructure.out.email.adapter.EmailAdapter;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.adapter.EmailHistoryAdapter;
import com.meli.delivery_weather_alert.infraestructure.out.email.EmailSender;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.adapter.ConfigurationRepositoryAdapter;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.repository.IConfigurationRepository;
import com.meli.delivery_weather_alert.infraestructure.out.persistence.repository.IEmailHistoryRespository;
import com.meli.delivery_weather_alert.infraestructure.out.weather.adapter.WeatherAPIAdapter;
import com.meli.delivery_weather_alert.infraestructure.out.weather.WeatherAPIClient;
import com.meli.delivery_weather_alert.domain.port.in.EmailHistoryServicePort;
import com.meli.delivery_weather_alert.domain.port.in.WeatherAlertServicePort;
import com.meli.delivery_weather_alert.domain.port.out.EmailHistoryPort;
import com.meli.delivery_weather_alert.domain.port.out.EmailSenderPort;
import com.meli.delivery_weather_alert.domain.port.out.ConfigurationRepositoryPort;
import com.meli.delivery_weather_alert.domain.port.out.WeatherProviderPort;
import com.meli.delivery_weather_alert.domain.service.EmailHistoryService;
import com.meli.delivery_weather_alert.domain.service.WeatherAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WeatherProviderPort weatherProviderPort(@Autowired WeatherAPIClient weatherAPIClient) {
        return new WeatherAPIAdapter(weatherAPIClient);
    }

    @Bean
    public ConfigurationRepositoryPort repositoryPort(@Autowired IConfigurationRepository configurationRepository) {
        return new ConfigurationRepositoryAdapter(configurationRepository);
    }

    @Bean
    public EmailSenderPort emailSenderPort(@Autowired EmailSender emailSender){
        return new EmailAdapter(emailSender);
    }

    @Bean
    public EmailHistoryPort emailHistoryPort (@Autowired IEmailHistoryRespository emailHistoryRespository){
        return new EmailHistoryAdapter(emailHistoryRespository);
    }

    @Bean
    public EmailHistoryServicePort emailHistoryServicePort (@Autowired EmailHistoryPort emailHistoryPort){
        return new EmailHistoryService(emailHistoryPort);
    }

    @Bean
    public WeatherAlertServicePort weatherAlertServicePort(@Value("${crypto.internal_key}") String internalKey, @Autowired WeatherProviderPort weatherProviderPort, @Autowired ConfigurationRepositoryPort configurationRepositoryPort, @Autowired EmailSenderPort emailSenderPort, @Autowired EmailHistoryServicePort emailHistoryServicePort) {
        return new WeatherAlertService(weatherProviderPort, configurationRepositoryPort, internalKey, emailSenderPort, emailHistoryServicePort);
    }

}
