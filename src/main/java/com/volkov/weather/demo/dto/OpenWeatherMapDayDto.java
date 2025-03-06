package com.volkov.weather.demo.dto;

import lombok.Data;

@Data
public class OpenWeatherMapDayDto {

    double temperatureCelsius;
    double windSpeed;
    String windDirection;
    double pressure;
    String pressureDescription;
    double humidity;
    String cloudiness;

}
