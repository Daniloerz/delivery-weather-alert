package com.meli.delivery_weather_alert.config;

import com.meli.delivery_weather_alert.adapter.out.email.EmailAdapter;
import com.meli.delivery_weather_alert.adapter.out.email.EmailSender;
import com.meli.delivery_weather_alert.adapter.out.persistence.adapter.RepositoryAdapter;
import com.meli.delivery_weather_alert.adapter.out.persistence.repository.IConfigurationRepository;
import com.meli.delivery_weather_alert.adapter.out.weather.WeatherAPIAdapter;
import com.meli.delivery_weather_alert.adapter.out.weather.WeatherAPIClient;
import com.meli.delivery_weather_alert.core.port.in.WeatherAlertServicePort;
import com.meli.delivery_weather_alert.core.port.out.EmailSenderPort;
import com.meli.delivery_weather_alert.core.port.out.RepositoryPort;
import com.meli.delivery_weather_alert.core.port.out.WeatherProviderPort;
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
    public EmailSenderPort emailSenderPort(@Autowired EmailSender emailSender){
        return new EmailAdapter(emailSender);
    }

    @Bean
    public WeatherAlertServicePort weatherAlertServicePort(@Value("${crypto.internal_key}") String internalKey, @Autowired WeatherProviderPort weatherProviderPort, @Autowired RepositoryPort repositoryPort, @Autowired EmailSenderPort emailSenderPort) {
        return new WeatherAlertService(weatherProviderPort, repositoryPort, internalKey, emailSenderPort);
    }

}
