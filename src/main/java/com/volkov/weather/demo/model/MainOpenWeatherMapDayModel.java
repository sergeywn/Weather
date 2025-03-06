package com.volkov.weather.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainOpenWeatherMapDayModel {

    @JsonProperty("temp")
    private double temp;

    @JsonProperty("grnd_level")
    private double groundLevel;

    @JsonProperty("humidity")
    private double humidity;

}
