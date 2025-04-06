package com.meli.delivery_weather_alert.adapter.in.controller;

import com.meli.delivery_weather_alert.adapter.in.dto.request.AlertRequestDto;
import com.meli.delivery_weather_alert.adapter.in.dto.response.AlertResponseDto;
import com.meli.delivery_weather_alert.adapter.in.dto.response.EmailHistoryResponseDto;
import com.meli.delivery_weather_alert.core.port.in.EmailHistoryServicePort;
import com.meli.delivery_weather_alert.core.port.in.WeatherAlertServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class WeatherAlertController {

    private final WeatherAlertServicePort weatherAlertServicePort;
    private final EmailHistoryServicePort emailHistoryServicePort;

    @PostMapping()
    public ResponseEntity<AlertResponseDto> weatherAlert(@RequestBody AlertRequestDto alertRequestDto){
        return ResponseEntity.ok(weatherAlertServicePort.sendAlert(alertRequestDto.getLatitude(), alertRequestDto.getLongitude(), alertRequestDto.getEmail()));
    }

    @GetMapping("/history")
    public ResponseEntity<List<EmailHistoryResponseDto>> getBuyerNotificationHistory(@RequestParam String email){
        return ResponseEntity.ok(emailHistoryServicePort.getBuyerNotificationHistory(email));
    }
}