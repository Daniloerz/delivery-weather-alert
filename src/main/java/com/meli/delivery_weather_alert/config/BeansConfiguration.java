package com.meli.delivery_weather_alert.config;

import com.meli.delivery_weather_alert.adapter.out.email.EmailAdapter;
import com.meli.delivery_weather_alert.adapter.out.email.EmailHistoryAdapter;
import com.meli.delivery_weather_alert.adapter.out.email.EmailSenderAdapter;
import com.meli.delivery_weather_alert.adapter.out.persistence.adapter.RepositoryAdapter;
import com.meli.delivery_weather_alert.adapter.out.persistence.repository.IConfigurationRepository;
import com.meli.delivery_weather_alert.adapter.out.persistence.repository.IEmailHistoryRespository;
import com.meli.delivery_weather_alert.adapter.out.weather.WeatherAPIAdapter;
import com.meli.delivery_weather_alert.adapter.out.weather.WeatherAPIClient;
import com.meli.delivery_weather_alert.core.port.in.EmailHistoryServicePort;
import com.meli.delivery_weather_alert.core.port.in.WeatherAlertServicePort;
import com.meli.delivery_weather_alert.core.port.out.EmailHistoryPort;
import com.meli.delivery_weather_alert.core.port.out.EmailSenderPort;
import com.meli.delivery_weather_alert.core.port.out.RepositoryPort;
import com.meli.delivery_weather_alert.core.port.out.WeatherProviderPort;
import com.meli.delivery_weather_alert.core.service.EmailHistoryService;
import com.meli.delivery_weather_alert.core.service.WeatherAlertService;
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
    public RepositoryPort repositoryPort(@Autowired IConfigurationRepository configurationRepository) {
        return new RepositoryAdapter(configurationRepository);
    }

    @Bean
    public EmailSenderPort emailSenderPort(@Autowired EmailSenderAdapter emailSenderAdapter){
        return new EmailAdapter(emailSenderAdapter);
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
    public WeatherAlertServicePort weatherAlertServicePort(@Value("${crypto.internal_key}") String internalKey, @Autowired WeatherProviderPort weatherProviderPort, @Autowired RepositoryPort repositoryPort, @Autowired EmailSenderPort emailSenderPort, @Autowired EmailHistoryServicePort emailHistoryServicePort) {
        return new WeatherAlertService(weatherProviderPort, repositoryPort, internalKey, emailSenderPort, emailHistoryServicePort);
    }

}
