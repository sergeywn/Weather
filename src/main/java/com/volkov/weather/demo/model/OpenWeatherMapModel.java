package com.volkov.weather.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapModel {

    @JsonProperty("main")
    private MainOpenWeatherMapDayModel main;

    @JsonProperty("wind")
    private WindOpenWeatherMapDayModel wind;

    @JsonProperty("clouds")
    private CloudsOpenWeatherMapDayModel clouds;

}
