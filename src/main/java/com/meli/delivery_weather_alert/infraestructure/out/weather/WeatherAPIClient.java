package com.meli.delivery_weather_alert.infraestructure.out.weather;

import com.meli.delivery_weather_alert.infraestructure.exceptions.WeatherAPIException;
import com.meli.delivery_weather_alert.infraestructure.exceptions.WeatherAPITechnicalException;
import com.meli.delivery_weather_alert.infraestructure.out.weather.dto.WeatherDto;
import com.meli.delivery_weather_alert.infraestructure.out.weather.response.Day;
import com.meli.delivery_weather_alert.infraestructure.out.weather.response.ForecastDay;
import com.meli.delivery_weather_alert.infraestructure.out.weather.response.Location;
import com.meli.delivery_weather_alert.infraestructure.out.weather.response.WeatherAPIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherAPIClient {

    private final RestTemplate restTemplate;

    private String WEATHER_API_URL;

    public WeatherAPIClient(RestTemplate restTemplate,  @Value("${weather_api.url}") String apiUrL) {
        this.restTemplate = restTemplate;
        this.WEATHER_API_URL = apiUrL;
    }

    public WeatherDto getForecastDays(String apiKey, String location, int days) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(WEATHER_API_URL)
                    .queryParam("key", apiKey)
                    .queryParam("q", location)
                    .queryParam("days", days)
                    .queryParam("lang", "es")
                    .toUriString();

            WeatherAPIResponse weatherResponse = restTemplate.getForObject(url, WeatherAPIResponse.class);

            if (weatherResponse == null || weatherResponse.getLocation() == null || weatherResponse.getForecast() == null) {
                throw new WeatherAPIException("Respuesta inválida de la API del clima");
            }

            Location locationResponse = weatherResponse.getLocation();
            Day dayResponse = weatherResponse.getForecast().getForecastday().stream()
                    .findFirst()
                    .map(ForecastDay::getDay)
                    .orElseThrow(() -> new WeatherAPIException("No se encontró información de pronóstico"));

            return new WeatherDto(
                    dayResponse.getCondition().getText(),
                    dayResponse.getCondition().getCode(),
                    locationResponse.getCountry(),
                    locationResponse.getName(),
                    locationResponse.getRegion()
            );
        } catch (WeatherAPIException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new WeatherAPITechnicalException("Error técnico al consultar la API del clima", ex);
        }
    }

}