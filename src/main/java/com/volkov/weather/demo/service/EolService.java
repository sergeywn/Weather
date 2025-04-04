package com.volkov.weather.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkov.weather.demo.dto.WeatherDto;
import com.volkov.weather.demo.mapper.*;
import com.volkov.weather.demo.model.WeatherEolModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("eolService")
@RequiredArgsConstructor
public class EolService implements WeatherService {

    private final DateTimeConverter dateTimeConverter;

    private final PressureMapper pressureMapper;

    private final WeatherMapper weatherMapper;

    private final WindMapper windMapper;

    private final DoubleRounder doubleRounder;
    private final String BASEURLPROJECTEOL = "https://projecteol.ru/api/weather/";

    private static final double PA_TO_MMHG_CONVERSION_FACTOR = 0.00750061683; // Коэффициент перевода из Па в мм рт. ст.

    private static final LocalDate now = LocalDate.now();
    private static final Double countTemperature = 273.15;
    private static final String projectEolToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzQwNjM4OTEzLCJpYXQiOjE3NDA2Mzg2MTMsImp0aSI6IjAxMzUwNjJlNGE0MjRjZGJiYzZlNzc0MGM5Zjc4NjExIiwidXNlcl9pZCI6MjA0fQ.Wo6bluyVWe3pfGIAfbia9_eOj1Or2g_9zRj3MpZ35lw";


    @Override
    public List<WeatherDto> getWeatherForDay(String latitude, String longitude) throws Exception {
        String urlProjectEol = BASEURLPROJECTEOL + "?lat=" + latitude + "&lon=" + longitude + "&date=" + now + "&token=" + projectEolToken;
        RestTemplate restTemplateEol = new RestTemplate();
        String responseEol = restTemplateEol.getForObject(urlProjectEol, String.class);

        ObjectMapper mapper = new ObjectMapper();
        List<WeatherEolModel> data2 = mapper.readValue(responseEol, new TypeReference<>() {
        });

        List<WeatherDto> weatherEolResults = data2.stream()
                .map(convert -> { // Здесь мы создаем новый объект WeatherEolResult на основе WeatherEolData
                    WeatherDto result = new WeatherDto();
                    result.setDatetime(dateTimeConverter.convertUtcToLocalTimezone(convert.getDateTime()));
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

    @Override
    public List<WeatherDto> getWeatherForFourDays(String latitude, String longitude) throws JsonProcessingException {
        RestTemplate restTemplateEol = new RestTemplate();

        LocalDateTime startDate = LocalDateTime.now().minusDays(1).withHour(21).withMinute(0);
        List<WeatherDto> weatherEolResults = new ArrayList<>();

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
                            WeatherDto result = new WeatherDto();
                            result.setDatetime(dateTimeConverter.convertUtcToLocalTimezoneForWeek(convert.getDateTime()));
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
}
