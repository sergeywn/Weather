package com.volkov.weather.demo.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = { WeatherMapper.class }) // Только если необходим полный контекст Spring
public class WeatherMapperTest {

    @InjectMocks
    private WeatherMapper weatherMapper;

    @Teпst
    public void testGetCloudinessDescription() {
        assertNotNull(weatherMapper); // Убедимся, что бин был успешно загружен

        // Проверим различные уровни облачности
        assertEquals("Ясная погода", weatherMapper.getCloudinessDescription(0));
        assertEquals("Ясная погода", weatherMapper.getCloudinessDescription(20));
        assertEquals("Немного облачно", weatherMapper.getCloudinessDescription(30));
        assertEquals("Частично облачная погода", weatherMapper.getCloudinessDescription(50));
        assertEquals("Преимущественно облачное небо", weatherMapper.getCloudinessDescription(70));
        assertEquals("Пасмурно", weatherMapper.getCloudinessDescription(90));
        assertEquals("Пасмурно", weatherMapper.getCloudinessDescription(100));
        assertEquals("Неверное значение облачности", weatherMapper.getCloudinessDescription(-1));
        assertEquals("Неверное значение облачности", weatherMapper.getCloudinessDescription(101));
    }
}