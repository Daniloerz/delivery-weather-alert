package com.meli.delivery_weather_alert.adapter.in.controller;

import com.meli.delivery_weather_alert.adapter.in.dto.request.AlertRequest;
import com.meli.delivery_weather_alert.core.port.in.WeatherAlertServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WeatherAlertController {

    private final WeatherAlertServicePort weatherAlertServicePort;

    @PostMapping("/send-alert")
    public ResponseEntity<String> weatherAlert(@RequestBody AlertRequest alertRequest){
        return ResponseEntity.ok(weatherAlertServicePort.sendAlert(alertRequest.getLatitude(), alertRequest.getLongitude(), alertRequest.getEmail()));
    }
}