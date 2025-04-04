package com.volkov.weather.demo.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.volkov.weather.demo.dto.WeatherDto;

import java.util.List;

public interface WeatherService {

    List<WeatherDto> getWeatherForDay(String latitude, String longitude) throws Exception;
    List<WeatherDto> getWeatherForFourDays(String latitude, String longitude) throws JsonProcessingException;

}
