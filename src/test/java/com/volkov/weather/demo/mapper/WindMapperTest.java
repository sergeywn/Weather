package com.volkov.weather.demo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = { WindMapper.class })
class WindMapperTest {

    @Autowired
    private WindMapper windMapper;

    @Test
    public void testGetWindDirectionText() {
        assertNotNull(windMapper); // Убедимся, что бин был успешно загружен

        // Проверим различные направления ветра
        assertEquals("Север", windMapper.getWindDirectionText(0));
        assertEquals("Север", windMapper.getWindDirectionText(10));
        assertEquals("Северо-восток", windMapper.getWindDirectionText(45));
        assertEquals("Восток", windMapper.getWindDirectionText(90));
        assertEquals("Юго-восток", windMapper.getWindDirectionText(135));
        assertEquals("Юг", windMapper.getWindDirectionText(180));
        assertEquals("Юго-запад", windMapper.getWindDirectionText(225));
        assertEquals("Запад", windMapper.getWindDirectionText(270));
        assertEquals("Северо-запад", windMapper.getWindDirectionText(315));
        assertEquals("Север", windMapper.getWindDirectionText(360)); // Оборачивание вокруг круга
        assertEquals("Неопределено", windMapper.getWindDirectionText(-1)); // Неверное значение
    }

}