package com.volkov.weather.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapFourDaysModel {

    @JsonProperty("list")
    private List<OpenWeatherMapListModel> list;

}
