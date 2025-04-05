package com.meli.delivery_weather_alert.core.service;

import com.meli.delivery_weather_alert.adapter.out.weather.response.Condition;
import com.meli.delivery_weather_alert.core.domain.model.Configuration;
import com.meli.delivery_weather_alert.core.domain.model.WeatherForecast;
import com.meli.delivery_weather_alert.core.port.in.WeatherAlertServicePort;
import com.meli.delivery_weather_alert.core.port.out.EmailSenderPort;
import com.meli.delivery_weather_alert.core.port.out.RepositoryPort;
import com.meli.delivery_weather_alert.core.port.out.WeatherProviderPort;
import com.meli.delivery_weather_alert.shared.utils.Utils;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WeatherAlertService implements WeatherAlertServicePort {

    String BODY_TEMPLATE ="Hola! Tenemos programada la entrega de tu paquete para mañana, en la dirección de entrega esperamos un día con %s y por esta razón es posible que tengamos retrasos. Haremos todo a nuestro alcance para cumplir con tu entrega.\n";

    private final WeatherProviderPort weatherProviderPort;
    private final RepositoryPort repositoryPort;
    private static final String API_KEY_NAME = "weather_api_key";
    private final List<Integer> blackList = List.of(1186, 1189, 1192, 1195);
    private final String internalKey;
    private final EmailSenderPort emailSenderPort;
    private static final String SUBJECT = "Mercado Libre: Entrega retrasada";

    @Override
    public String sendAlert(String latitude, String longitude, String email) {

        String location = String.join(",", latitude, longitude);

        final String apiKey = Optional.ofNullable(repositoryPort.findConfigurationByName(API_KEY_NAME))
                .map(configuration -> Utils.decrypt(configuration.getValue(), internalKey)).orElse(Strings.EMPTY);
        final WeatherForecast forecastDays = weatherProviderPort.getForecastDays(apiKey, location, 1);

        if(sendAlert(forecastDays)){
            sendEmail(email, forecastDays.getText());
            return "Mensaje enviado. El pedido presenta demoras por condición climatica. Revisar en correo no deseado";
        }

        return "Mensaje no enviado, el pedido no presenta demoras";
    }

    private boolean sendAlert(WeatherForecast forecast){
        return blackList.contains(forecast.getCode());
    }

    private void sendEmail(String email, String text){

        String body = String.format(BODY_TEMPLATE, text);
        emailSenderPort.sendEmail(email, SUBJECT,body);
    }

}