package com.meli.delivery_weather_alert.domain.service;

import com.meli.delivery_weather_alert.infraestructure.in.dto.response.AlertResponseDto;
import com.meli.delivery_weather_alert.domain.model.EmailHistory;
import com.meli.delivery_weather_alert.domain.model.WeatherForecast;
import com.meli.delivery_weather_alert.domain.port.in.EmailHistoryServicePort;
import com.meli.delivery_weather_alert.domain.port.in.WeatherAlertServicePort;
import com.meli.delivery_weather_alert.domain.port.out.EmailSenderPort;
import com.meli.delivery_weather_alert.domain.port.out.ConfigurationRepositoryPort;
import com.meli.delivery_weather_alert.domain.port.out.WeatherProviderPort;
import com.meli.delivery_weather_alert.shared.utils.Utils;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WeatherAlertService implements WeatherAlertServicePort {

    String BODY_TEMPLATE ="Hola! Tenemos programada la entrega de tu paquete para mañana, en la dirección de entrega esperamos un día con %s y por esta razón es posible que tengamos retrasos. Haremos todo a nuestro alcance para cumplir con tu entrega.\n";

    private final WeatherProviderPort weatherProviderPort;
    private final ConfigurationRepositoryPort configurationRepositoryPort;
    private static final String API_KEY_NAME = "weather_api_key";
    private final List<Integer> blackList = List.of(1186, 1189, 1192, 1195);
    private final String internalKey;
    private final EmailSenderPort emailSenderPort;
    private static final String SUBJECT = "Mercado Libre: Entrega retrasada";
    private final EmailHistoryServicePort emailHistoryServicePort;

    @Override
    public AlertResponseDto sendAlert(String latitude, String longitude, String email) {

        String location = String.join(",", latitude, longitude);

        final String apiKey = Optional.ofNullable(configurationRepositoryPort.findConfigurationByName(API_KEY_NAME))
                .map(configuration -> Utils.decrypt(configuration.getValue(), internalKey)).orElse(Strings.EMPTY);
        final WeatherForecast forecastDays = weatherProviderPort.getForecastDays(apiKey, location, 1);

        if(sendAlert(forecastDays)){
            sendEmail(email, forecastDays.getText());
            saveBuyerNotification(forecastDays, email);
            return new AlertResponseDto(forecastDays.getCode(), forecastDays.getText(),true);
        }

        return new AlertResponseDto(forecastDays.getCode(), forecastDays.getText(),false);
    }

    private boolean sendAlert(WeatherForecast forecast){
        return blackList.contains(forecast.getCode());
    }

    private void sendEmail(String email, String text){

        String body = String.format(BODY_TEMPLATE, text);
        emailSenderPort.sendEmail(email, SUBJECT,body);
    }

    private void saveBuyerNotification (WeatherForecast weatherForecast, String email){
        EmailHistory emailHistory = new EmailHistory(email, new Date(), weatherForecast.getCountry(), weatherForecast.getLocationName(), weatherForecast.getRegion(), weatherForecast.getCode(), weatherForecast.getText());
        emailHistoryServicePort.saveBuyerEmailNotification(emailHistory);
    }

}