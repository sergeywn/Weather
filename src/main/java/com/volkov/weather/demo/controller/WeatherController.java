package com.volkov.weather.demo.controller;

import com.volkov.weather.demo.dto.Coordinates;
import com.volkov.weather.demo.dto.WeatherDto;
import com.volkov.weather.demo.service.AllWeatherService;
import com.volkov.weather.demo.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WeatherController {

    private final WeatherService eolService;
    private final WeatherService openWeatherMapService;
    private final AllWeatherService allWeatherService;

    @PostMapping(value = "/{provider}/{forecastType}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<WeatherDto> getWeather(
            @PathVariable String provider,
            @PathVariable String forecastType,
            @RequestBody Coordinates coordinates) throws Exception {

        String latitude = coordinates.getLatitude();
        String longitude = coordinates.getLongitude();

        return switch (provider) {
            case "eol" -> getWeatherFromService(eolService, forecastType, latitude, longitude);
            case "openweathermap" -> getWeatherFromService(openWeatherMapService, forecastType, latitude, longitude);
            case "all" -> allWeatherService.getWeather(latitude, longitude, forecastType);
            default -> throw new IllegalArgumentException("Неизвестный провайдер: " + provider);
        };
    }

    private List<WeatherDto> getWeatherFromService(WeatherService service, String forecastType, String latitude, String longitude) throws Exception {
        if ("day".equals(forecastType)) {
            return service.getWeatherForDay(latitude, longitude);
        } else if ("fourDays".equals(forecastType)) {
            return service.getWeatherForFourDays(latitude, longitude);
        } else {
            throw new IllegalArgumentException("Неизвестный тип прогноза: " + forecastType);
        }
    }

}
