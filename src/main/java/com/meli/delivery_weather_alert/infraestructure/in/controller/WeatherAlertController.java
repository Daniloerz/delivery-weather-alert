package com.meli.delivery_weather_alert.infraestructure.in.controller;

import com.meli.delivery_weather_alert.infraestructure.in.dto.request.AlertRequestDto;
import com.meli.delivery_weather_alert.infraestructure.in.dto.response.AlertResponseDto;
import com.meli.delivery_weather_alert.infraestructure.in.dto.response.EmailHistoryResponseDto;
import com.meli.delivery_weather_alert.domain.port.in.EmailHistoryServicePort;
import com.meli.delivery_weather_alert.domain.port.in.WeatherAlertServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
    @Operation(
            summary = "Enviar alerta de clima",
            description = "Este endpoint envía una alerta de clima al correo del comprador basado en las coordenadas proporcionadas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta enviada exitosamente")
    })
    public ResponseEntity<AlertResponseDto> weatherAlert(@Valid @RequestBody AlertRequestDto alertRequestDto){
        return ResponseEntity.ok(weatherAlertServicePort.sendAlert(
                alertRequestDto.getLatitude(),
                alertRequestDto.getLongitude(),
                alertRequestDto.getEmail()
        ));
    }

    @GetMapping("/history")
    @Operation(
            summary = "Obtener historial de notificaciones de un comprador",
            description = "Este endpoint devuelve el historial de notificaciones enviadas a un comprador con el correo electrónico proporcionado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial obtenido exitosamente")
           })
    public ResponseEntity<List<EmailHistoryResponseDto>> getBuyerNotificationHistory(
            @RequestParam @Parameter(description = "Correo electrónico del comprador") String email
    ){
        return ResponseEntity.ok(emailHistoryServicePort.getBuyerNotificationHistory(email));
    }
}
