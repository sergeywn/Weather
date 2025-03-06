package com.volkov.weather.demo.dto;

import lombok.Data;
import lombok.Value;

@Data
public class WeatherEolDto {

    String dateTime;
    double temperatureCelsius;
    double windSpeed;
    String windDirection;
    double pressure;
    String pressureDescription;
    double humidity;
    String cloudiness;

}
