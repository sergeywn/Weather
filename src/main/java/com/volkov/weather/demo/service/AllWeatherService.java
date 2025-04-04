package com.volkov.weather.demo.service;

import com.volkov.weather.demo.dto.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class AllWeatherService {

    private final List<WeatherService> weatherServices;

    public List<WeatherDto> getWeather(String latitude, String longitude, String forecastType) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(weatherServices.size()); // Пул потоков
        List<Future<List<WeatherDto>>> futures = new ArrayList<>();

        // Запуск задач в отдельных потоках
        for (WeatherService service : weatherServices) {
            futures.add(executor.submit(() -> {
                if ("day".equals(forecastType)) {
                    return service.getWeatherForDay(latitude, longitude);
                } else if ("fourDays".equals(forecastType)) {
                    return service.getWeatherForFourDays(latitude, longitude);
                } else {
                    throw new IllegalArgumentException("Неизвестный тип прогноза: " + forecastType);
                }
            }));
        }

        List<WeatherDto> results = new ArrayList<>();

        // Обработка результатов
        for (Future<List<WeatherDto>> future : futures) {
            try {
                results.addAll(future.get());
            } catch (ExecutionException e) {
                // Логируем ошибку, но продолжаем работу
                System.err.println("Ошибка при получении данных: " + e.getMessage());
            }
        }

        executor.shutdown(); // Завершаем пул потоков
        return results;
    }

}
