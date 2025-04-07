package com.meli.delivery_weather_alert.infraestructure.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.delivery_weather_alert.infraestructure.in.dto.request.AlertRequestDto;
import com.meli.delivery_weather_alert.infraestructure.in.dto.response.AlertResponseDto;
import com.meli.delivery_weather_alert.infraestructure.in.dto.response.EmailHistoryResponseDto;
import com.meli.delivery_weather_alert.domain.port.in.EmailHistoryServicePort;
import com.meli.delivery_weather_alert.domain.port.in.WeatherAlertServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WeatherAlertControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private WeatherAlertServicePort weatherAlertServicePort;
    private EmailHistoryServicePort emailHistoryServicePort;

    private AlertRequestDto validRequest;
    private AlertResponseDto alertResponse;

    @BeforeEach
    void setUp() {
        weatherAlertServicePort = Mockito.mock(WeatherAlertServicePort.class);
        emailHistoryServicePort = Mockito.mock(EmailHistoryServicePort.class);

        WeatherAlertController controller = new WeatherAlertController(
                weatherAlertServicePort, emailHistoryServicePort
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        objectMapper = new ObjectMapper();

        validRequest = new AlertRequestDto();
        validRequest.setEmail("test@example.com");
        validRequest.setLatitude("12.34");
        validRequest.setLongitude("56.78");

        alertResponse = new AlertResponseDto(200, "Despejado", true);
    }

    @Test
    void weatherAlert_shouldReturnOk_whenValidRequest() throws Exception {
        Mockito.when(weatherAlertServicePort.sendAlert("12.34", "56.78", "test@example.com"))
                .thenReturn(alertResponse);

        mockMvc.perform(post("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.forecaste_code").value(200))
                .andExpect(jsonPath("$.forecast_descripcion").value("Despejado"))
                .andExpect(jsonPath("$.buyer_notification").value(true));
    }

    @Test
    void weatherAlert_shouldReturnBadRequest_whenInvalidEmail() throws Exception {
        validRequest.setEmail("invalid-email");

        mockMvc.perform(post("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void weatherAlert_shouldReturnBadRequest_whenLatitudeEmpty() throws Exception {
        validRequest.setLatitude("");

        mockMvc.perform(post("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void weatherAlert_shouldReturnBadRequest_whenLongitudeNull() throws Exception {
        validRequest.setLongitude(null);

        mockMvc.perform(post("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBuyerNotificationHistory_shouldReturnList() throws Exception {
        EmailHistoryResponseDto history = new EmailHistoryResponseDto(
                "test@example.com",
                new Date(1712400000000L),
                "Colombia",
                "Bogotá",
                "Cundinamarca",
                200,
                "Despejado"
        );

        Mockito.when(emailHistoryServicePort.getBuyerNotificationHistory("test@example.com"))
                .thenReturn(List.of(history));

        mockMvc.perform(get("/api/notification/history")
                        .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].buyerEmail").value("test@example.com"))
                .andExpect(jsonPath("$[0].country").value("Colombia"))
                .andExpect(jsonPath("$[0].locationName").value("Bogotá"))
                .andExpect(jsonPath("$[0].region").value("Cundinamarca"))
                .andExpect(jsonPath("$[0].forecastCode").value(200))
                .andExpect(jsonPath("$[0].weatherDescription").value("Despejado"));
    }
}
