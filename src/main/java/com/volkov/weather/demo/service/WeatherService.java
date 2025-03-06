package com.volkov.weather.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkov.weather.demo.dto.OpenWeatherMapDayDto;
import com.volkov.weather.demo.dto.OpenWeatherMapFourDaysDto;
import com.volkov.weather.demo.dto.WeatherEolDto;
import com.volkov.weather.demo.mapper.*;

import com.volkov.weather.demo.model.OpenWeatherMapFourDaysModel;
import com.volkov.weather.demo.model.OpenWeatherMapModel;
import com.volkov.weather.demo.model.WeatherEolModel;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final DateTimeConverter dateTimeConverter;

    private final PressureMapper pressureMapper;

    private final WeatherMapper weatherMapper;

    private final WindMapper windMapper;

    private final DoubleRounder doubleRounder;
    private final UnixTimeConverter unixTimeConverter;
    private final String BASEURLPROJECTEOL = "https://projecteol.ru/api/weather/";
    private final String BASEURLOPENWEATHERMAP = "https://api.openweathermap.org/data/2.5/";
    private static final double PA_TO_MMHG_CONVERSION_FACTOR = 0.00750061683; // Коэффициент перевода из Па в мм рт. ст.
    public static final double GPA_TO_MMHG_CONVERSION_FACTOR = 0.750062; // Коэффициент перевода из гПа в мм рт. ст.
    private static final LocalDate now = LocalDate.now();
    private static final Double countTemperature = 273.15;
    private static final String projectEolToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzQwNjM4OTEzLCJpYXQiOjE3NDA2Mzg2MTMsImp0aSI6IjAxMzUwNjJlNGE0MjRjZGJiYzZlNzc0MGM5Zjc4NjExIiwidXNlcl9pZCI6MjA0fQ.Wo6bluyVWe3pfGIAfbia9_eOj1Or2g_9zRj3MpZ35lw";
    public static final String openWeatherMapToken = "5b8c942c6e6858ad46ec3d9ff2f21031";

    public List<WeatherEolDto> weatherEolDay(String latitude, String longitude) throws JsonProcessingException {

        String urlProjectEol = BASEURLPROJECTEOL + "?lat=" + latitude + "&lon=" + longitude + "&date=" + now + "&token=" + projectEolToken;
        RestTemplate restTemplateEol = new RestTemplate();
        String responseEol = restTemplateEol.getForObject(urlProjectEol, String.class);

        ObjectMapper mapper = new ObjectMapper();
        List<WeatherEolModel> data2 = mapper.readValue(responseEol, new TypeReference<>() {
        });

        List<WeatherEolDto> weatherEolResults = data2.stream()
                .map(convert -> { // Здесь мы создаем новый объект WeatherEolResult на основе WeatherEolData
                    WeatherEolDto result = new WeatherEolDto();
                    result.setDateTime(dateTimeConverter.convertUtcToLocalTimezone(convert.getDateTime()));
                    result.setTemperatureCelsius(doubleRounder.round(convert.getTemperatureKelvin() - countTemperature, 1));
                    result.setHumidity(doubleRounder.round(convert.getVlaga(), 1));
                    result.setCloudiness(weatherMapper.getCloudinessDescription(convert.getOblachnost()));
                    result.setPressure(doubleRounder.round(convert.getPres() * PA_TO_MMHG_CONVERSION_FACTOR, 1));
                    result.setPressureDescription(pressureMapper.evaluatePressure((convert.getPres() * PA_TO_MMHG_CONVERSION_FACTOR)));
                    result.setWindSpeed(doubleRounder.round(convert.getWindSpeed(), 1));
                    result.setWindDirection(windMapper.getWindDirectionText(convert.getWindDir()));

                    // Добавляем другие необходимые поля
                    return result;
                })
                .collect(Collectors.toList());
        return weatherEolResults;
    }

    public List<WeatherEolDto> weatherEolWeek(String latitude, String longitude) throws JsonProcessingException {
        RestTemplate restTemplateEol = new RestTemplate();

        LocalDateTime startDate = LocalDateTime.now().minusDays(1).withHour(21).withMinute(0);
        List<WeatherEolDto> weatherEolResults = new ArrayList<>();

        for (int i = 0; i < 4; i++) { // для каждой недели
            for (int hour : Arrays.asList(3, 9, 15, 21)) { // каждый день в 00:00, 06:00, 12:00, 18:00
                LocalDateTime dateTime = startDate.plusDays(i).plusHours(hour);

                // Формируем полный URL с параметрами
                String urlProjectEol = BASEURLPROJECTEOL + "?lat=" + latitude + "&lon=" + longitude +
                        "&date=" + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) + "&token=" + projectEolToken;

                // Выполняем GET-запрос
                String responseEol = restTemplateEol.getForObject(urlProjectEol, String.class);

                ObjectMapper mapper = new ObjectMapper();
                List<WeatherEolModel> data2 = mapper.readValue(responseEol, new TypeReference<>() {
                });

                weatherEolResults.addAll(data2.stream()
                        .map(convert -> {
                            WeatherEolDto result = new WeatherEolDto();
                            result.setDateTime(dateTimeConverter.convertUtcToLocalTimezoneForWeek(convert.getDateTime()));
                            result.setTemperatureCelsius(doubleRounder.round(convert.getTemperatureKelvin() - countTemperature, 1));
                            result.setHumidity(doubleRounder.round(convert.getVlaga(), 1));
                            result.setCloudiness(weatherMapper.getCloudinessDescription(convert.getOblachnost()));
                            result.setPressure(doubleRounder.round(convert.getPres() * PA_TO_MMHG_CONVERSION_FACTOR, 1));
                            result.setPressureDescription(pressureMapper.evaluatePressure(convert.getPres() * PA_TO_MMHG_CONVERSION_FACTOR));
                            result.setWindSpeed(doubleRounder.round(convert.getWindSpeed(), 1));
                            result.setWindDirection(windMapper.getWindDirectionText(convert.getWindDir()));

                            // Добавляем другие необходимые поля
                            return result;
                        }).collect(Collectors.toList())
                );
            }
        }
        return weatherEolResults;
    }

    public OpenWeatherMapDayDto openWeatherMapDay(String latitude, String longitude) throws JsonProcessingException {

        String urlOpenWeatherMap = BASEURLOPENWEATHERMAP + "weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + openWeatherMapToken + "&units=metric&lang=ru";

        RestTemplate restTemplateEol = new RestTemplate();

        String responseOpenWeather = restTemplateEol.getForObject(urlOpenWeatherMap, String.class);

        ObjectMapper mapper = new ObjectMapper();

        OpenWeatherMapModel dataOpenWeather = mapper.readValue(responseOpenWeather, new TypeReference<>() {
        });

        OpenWeatherMapDayDto openWeatherMapDayDto = new OpenWeatherMapDayDto();

        openWeatherMapDayDto.setTemperatureCelsius(doubleRounder.round(dataOpenWeather.getMain().getTemp(), 1));
        openWeatherMapDayDto.setWindSpeed(doubleRounder.round(dataOpenWeather.getWind().getSpeed(), 1));
        openWeatherMapDayDto.setWindDirection(windMapper.getWindDirectionText(dataOpenWeather.getWind().getDeg()));
        openWeatherMapDayDto.setPressure(doubleRounder.round(dataOpenWeather.getMain().getGroundLevel() * GPA_TO_MMHG_CONVERSION_FACTOR, 1));
        openWeatherMapDayDto.setPressureDescription(pressureMapper.evaluatePressure(dataOpenWeather.getMain().getGroundLevel() * GPA_TO_MMHG_CONVERSION_FACTOR));
        openWeatherMapDayDto.setHumidity(doubleRounder.round(dataOpenWeather.getMain().getHumidity(), 1));
        openWeatherMapDayDto.setCloudiness(weatherMapper.getCloudinessDescription(dataOpenWeather.getClouds().getAll()));

        return openWeatherMapDayDto;
    }

    public List<OpenWeatherMapFourDaysDto> openWeatherMapFourDaysDto(String latitude, String longitude) throws JsonProcessingException {

        String urlOpenWeatherMap = BASEURLOPENWEATHERMAP + "forecast?lat=" + latitude + "&lon=" + longitude + "&appid=" + openWeatherMapToken + "&units=metric&lang=ru";

        // Форматтер для преобразования строки в LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
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
                    // Преобразуем dt_txt в LocalDate
                    LocalDate date = LocalDate.parse(unixTimeConverter.convertUnixTime(convert.getDateTime()), formatter);
                    // Оставляем только данные, которые попадают в диапазон [startDate, endDate)
                    return !date.isBefore(startDate) && date.isBefore(endDate);
                })
                .map(convert -> {
                    OpenWeatherMapFourDaysDto result = new OpenWeatherMapFourDaysDto();
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
