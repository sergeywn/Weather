package com.volkov.weather.demo.mapper;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = { WindMapper.class })
@Tag("Mapper")
class WindMapperTest {

    @Autowired
    private WindMapper windMapper;

    @Test
    public void testGetWindDirectionText() {
        // Убедимся, что бин был успешно загружен
        assertThat(windMapper).isNotNull();

        // Проверим различные направления ветра
        assertThat(windMapper.getWindDirectionText(0)).isEqualTo("Север");
        assertThat(windMapper.getWindDirectionText(10)).isEqualTo("Север");
        assertThat(windMapper.getWindDirectionText(45)).isEqualTo("Северо-восток");
        assertThat(windMapper.getWindDirectionText(90)).isEqualTo("Восток");
        assertThat(windMapper.getWindDirectionText(135)).isEqualTo("Юго-восток");
        assertThat(windMapper.getWindDirectionText(180)).isEqualTo("Юг");
        assertThat(windMapper.getWindDirectionText(225)).isEqualTo("Юго-запад");
        assertThat(windMapper.getWindDirectionText(270)).isEqualTo("Запад");
        assertThat(windMapper.getWindDirectionText(315)).isEqualTo("Северо-запад");
        assertThat(windMapper.getWindDirectionText(360)).isEqualTo("Север"); // Оборачивание вокруг круга
        assertThat(windMapper.getWindDirectionText(-1)).isEqualTo("Неопределено"); // Неверное значение
    }

}