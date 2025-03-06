package com.volkov.weather.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherEolModel {

    @JsonProperty("dt_forecast")
    String dateTime;
    @JsonProperty("temp_2")
    Double temperatureKelvin;
    @JsonProperty("wind_speed_10")
    Double windSpeed;
    @JsonProperty("wind_dir_10")
    Double windDir;
    @JsonProperty("pres_surf")
    Double pres;
    @JsonProperty("vlaga_2")
    Double vlaga;
    @JsonProperty("oblachnost_atmo")
    Double oblachnost;

}
