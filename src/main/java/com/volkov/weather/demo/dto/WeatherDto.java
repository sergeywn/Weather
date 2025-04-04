package com.volkov.weather.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WeatherDto {

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("temperatureCelsius")
    private Double temperatureCelsius;

    @JsonProperty("windSpeed")
    private Double windSpeed;

    @JsonProperty("windDirection")
    private String windDirection;

    @JsonProperty("pressure")
    private Double pressure;

    @JsonProperty("pressureDescription")
    private String pressureDescription;

    @JsonProperty("humidity")
    private Double humidity;

    @JsonProperty("cloudiness")
    private String cloudiness;

}
