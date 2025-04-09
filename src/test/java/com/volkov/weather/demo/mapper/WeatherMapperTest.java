package com.volkov.weather.demo.mapper;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = { WeatherMapper.class }) // Только если необходим полный контекст Spring
@Tag("Mapper")
public class WeatherMapperTest {

    @InjectMocks
    private WeatherMapper weatherMapper;

    @Test
    public void testGetCloudinessDescription() {
        // Убедимся, что бин был успешно загружен
        assertThat(weatherMapper).isNotNull();

        // Проверим различные уровни облачности
        assertThat(weatherMapper.getCloudinessDescription(0)).isEqualTo("Ясная погода");
        assertThat(weatherMapper.getCloudinessDescription(20)).isEqualTo("Ясная погода");
        assertThat(weatherMapper.getCloudinessDescription(30)).isEqualTo("Немного облачно");
        assertThat(weatherMapper.getCloudinessDescription(50)).isEqualTo("Частично облачная погода");
        assertThat(weatherMapper.getCloudinessDescription(70)).isEqualTo("Преимущественно облачное небо");
        assertThat(weatherMapper.getCloudinessDescription(90)).isEqualTo("Пасмурно");
        assertThat(weatherMapper.getCloudinessDescription(100)).isEqualTo("Пасмурно");
        assertThat(weatherMapper.getCloudinessDescription(-1)).isEqualTo("Неверное значение облачности");
        assertThat(weatherMapper.getCloudinessDescription(101)).isEqualTo("Неверное значение облачности");
    }
}