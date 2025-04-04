package com.volkov.weather.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkov.weather.demo.dto.WeatherDto;
import com.volkov.weather.demo.mapper.*;
import com.volkov.weather.demo.model.OpenWeatherMapFourDaysModel;
import com.volkov.weather.demo.model.OpenWeatherMapModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service("openWeatherMapService")
@RequiredArgsConstructor
public class OpenWeatherMapService implements WeatherService {

    private final PressureMapper pressureMapper;

    private final WeatherMapper weatherMapper;

    private final WindMapper windMapper;

    private final DoubleRounder doubleRounder;
    private final UnixTimeConverter unixTimeConverter;
    private final String BASEURLOPENWEATHERMAP = "https://api.openweathermap.org/data/2.5/";
    public static final double GPA_TO_MMHG_CONVERSION_FACTOR = 0.750062; // Коэффициент перевода из гПа в мм рт. ст.
    public static final String openWeatherMapToken = "5b8c942c6e6858ad46ec3d9ff2f21031";

    @Override
    public List<WeatherDto> getWeatherForDay(String latitude, String longitude) throws Exception {
        String urlOpenWeatherMap = BASEURLOPENWEATHERMAP + "weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + openWeatherMapToken + "&units=metric&lang=ru";

        RestTemplate restTemplateEol = new RestTemplate();

        String responseOpenWeather = restTemplateEol.getForObject(urlOpenWeatherMap, String.class);

        ObjectMapper mapper = new ObjectMapper();

        OpenWeatherMapModel dataOpenWeather = mapper.readValue(responseOpenWeather, new TypeReference<>() {
        });

        WeatherDto weatherDto = new WeatherDto();

        weatherDto.setTemperatureCelsius(doubleRounder.round(dataOpenWeather.getMain().getTemp(), 1));
        weatherDto.setWindSpeed(doubleRounder.round(dataOpenWeather.getWind().getSpeed(), 1));
        weatherDto.setWindDirection(windMapper.getWindDirectionText(dataOpenWeather.getWind().getDeg()));
        weatherDto.setPressure(doubleRounder.round(dataOpenWeather.getMain().getGroundLevel() * GPA_TO_MMHG_CONVERSION_FACTOR, 1));
        weatherDto.setPressureDescription(pressureMapper.evaluatePressure(dataOpenWeather.getMain().getGroundLevel() * GPA_TO_MMHG_CONVERSION_FACTOR));
        weatherDto.setHumidity(doubleRounder.round(dataOpenWeather.getMain().getHumidity(), 1));
        weatherDto.setCloudiness(weatherMapper.getCloudinessDescription(dataOpenWeather.getClouds().getAll()));

        return List.of(weatherDto);
    }

    @Override
    public List<WeatherDto> getWeatherForFourDays(String latitude, String longitude) throws JsonProcessingException {
        String urlOpenWeatherMap = BASEURLOPENWEATHERMAP + "forecast?lat=" + latitude + "&lon=" + longitude + "&appid=" + openWeatherMapToken + "&units=metric&lang=ru";

        // Форматтер для преобразования строки в LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        // Определяем диапазон дат (4 дня, начиная с текущей даты)
        LocalDate startDate = LocalDate.now(); // Текущая дата
        LocalDate endDate = startDate.plusDays(4); // Дата через 4 дня

        RestTemplate restTemplateEol = new RestTemplate();

        String responseOpenWeather = restTemplateEol.getForObject(urlOpenWeatherMap, String.class);

        ObjectMapper mapper = new ObjectMapper();

        OpenWeatherMapFourDaysModel dataOpenWeather = mapper.readValue(responseOpenWeather, new TypeReference<>() {
        });

        return dataOpenWeather.getList().stream()
                .filter(convert -> {
                    // Преобразуем dt_txt в LocalDateTime
                    LocalDateTime dateTime = LocalDateTime.parse(unixTimeConverter.convertUnixTime(convert.getDateTime()), formatter);
                    // Оставляем только данные, которые попадают в диапазон [startDate, endDate)
                    boolean isWithinDateRange = !dateTime.toLocalDate().isBefore(startDate) && dateTime.toLocalDate().isBefore(endDate);
                    // Оставляем только данные с временем 00:00, 06:00, 12:00 или 18:00
                    int hour = dateTime.getHour();
                    boolean isDesiredTime = hour == 0 || hour == 6 || hour == 12 || hour == 18;
                    return isWithinDateRange && isDesiredTime;
                })
                .map(convert -> {
                    WeatherDto result = new WeatherDto();
                    result.setDatetime(unixTimeConverter.convertUnixTime(convert.getDateTime()));
                    result.setTemperatureCelsius(doubleRounder.round(convert.getMain().getTemp(), 1));
                    result.setPressure(doubleRounder.round(convert.getMain().getGroundLevel() * GPA_TO_MMHG_CONVERSION_FACTOR, 1));
                    result.setPressureDescription(pressureMapper.evaluatePressure(convert.getMain().getGroundLevel() * GPA_TO_MMHG_CONVERSION_FACTOR));
                    result.setHumidity(doubleRounder.round(convert.getMain().getHumidity(), 1));
                    result.setCloudiness(weatherMapper.getCloudinessDescription(convert.getClouds().getAll()));
                    result.setWindSpeed(doubleRounder.round(convert.getWind().getSpeed(), 1));
                    result.setWindDirection(windMapper.getWindDirectionText(convert.getWind().getDeg()));
                    return result;
                }).collect(Collectors.toList());
    }
}
