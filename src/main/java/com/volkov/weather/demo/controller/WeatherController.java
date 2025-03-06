package com.volkov.weather.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.volkov.weather.demo.dto.Coordinates;
import com.volkov.weather.demo.dto.OpenWeatherMapDayDto;
import com.volkov.weather.demo.dto.OpenWeatherMapFourDaysDto;
import com.volkov.weather.demo.dto.WeatherEolDto;
import com.volkov.weather.demo.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping(value = "/eol-day", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WeatherEolDto>> getWeatherForDayFromEol(@RequestBody Coordinates coordinates) {
        try {
            List<WeatherEolDto> result = weatherService.weatherEolDay(coordinates.latitude(), coordinates.longitude());
            return ResponseEntity.ok(result); // успешный результат
        } catch (Exception e) { // Обрабатываем возможные исключения
            log.error("Ошибка при получении погоды: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // ошибка сервера
        }
    }

    @PostMapping(value = "/eol-week", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WeatherEolDto>> getWeatherForWeekFromEol(@RequestBody Coordinates coordinates) {
        try {
            List<WeatherEolDto> result = weatherService.weatherEolWeek(coordinates.latitude(), coordinates.longitude());
            return ResponseEntity.ok(result); // успешный результат
        } catch (Exception e) { // Обрабатываем возможные исключения
            log.error("Ошибка при получении погоды: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // ошибка сервера
        }
    }

    @PostMapping(value = "/open-weather-map-day", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OpenWeatherMapDayDto> getWeatherForDayFromOpenWeatherMap(@RequestBody Coordinates coordinates) {
        try {
            OpenWeatherMapDayDto result = weatherService.openWeatherMapDay(coordinates.latitude(), coordinates.longitude());
            return ResponseEntity.ok(result); // успешный результат
        } catch (Exception e) { // Обрабатываем возможные исключения
            log.error("Ошибка при получении погоды: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // ошибка сервера
        }
    }

    @PostMapping(value = "/open-weather-map-four-days", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OpenWeatherMapFourDaysDto>> getWeatherForFourDaysFromOpenWeatherMap(@RequestBody Coordinates coordinates) {
        try {
            List<OpenWeatherMapFourDaysDto> result = weatherService.openWeatherMapFourDaysDto(coordinates.latitude(), coordinates.longitude());
            return ResponseEntity.ok(result); // успешный результат
        } catch (Exception e) { // Обрабатываем возможные исключения
            log.error("Ошибка при получении погоды: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // ошибка сервера
        }
    }

}
