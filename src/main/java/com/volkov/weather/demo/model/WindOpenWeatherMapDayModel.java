package com.volkov.weather.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WindOpenWeatherMapDayModel {

    @JsonProperty("speed")
    private double speed;

    @JsonProperty("deg")
    private int deg;

}
