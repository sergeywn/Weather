package com.volkov.weather.demo.repository;

import com.volkov.weather.demo.model.CurrentWeatherDto;
import com.volkov.weather.demo.model.WeeklyWeatherDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
/*
@Repository
public class YandexWeatherRepository {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    public YandexWeatherRepository(RestTemplate restTemplate, @Value("${app.yandex.base-url}") String baseUrl, @Value("${app.yandex.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public CurrentWeatherDto getCurrentWeather(double lat, double lon) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Yandex-API-Key", apiKey);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("forecast")
                .queryParam("lat", lat)
                .queryParam("lon", lon);

        HttpEntity<?> entity = new HttpEntity<>(null, headers);

        ResponseEntity<CurrentWeatherDto> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                CurrentWeatherDto.class
        );

        return response.getBody();
    }

    public WeeklyWeatherDto getWeeklyWeather(double lat, double lon) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Yandex-API-Key", apiKey);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("forecast")
                .queryParam("lat", lat)
                .queryParam("lon", lon);

        HttpEntity<?> entity = new HttpEntity<>(null, headers);

        ResponseEntity<WeeklyWeatherDto> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                WeeklyWeatherDto.class
        );

        return response.getBody();
    }

}
*/