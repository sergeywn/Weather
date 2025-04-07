package com.volkov.weather.demo.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = { PressureMapper.class }) // Только если необходим полный контекст Spring
public class PressureMapperTest {

    @InjectMocks
    private PressureMapper pressureMapper;

    @Test
    public void testEvaluatePressure() {
        assertNotNull(pressureMapper); // Убедимся, что бин был успешно загружен

        // Проверим различные уровни давления
        assertEquals("Очень низкое давление", pressureMapper.evaluatePressure(749));
        assertEquals("Низкое давление", pressureMapper.evaluatePressure(752));
        assertEquals("Среднее давление", pressureMapper.evaluatePressure(757));
        assertEquals("Нормальное давление", pressureMapper.evaluatePressure(764));
        assertEquals("Высокое давление", pressureMapper.evaluatePressure(769));
        assertEquals("Очень высокое давление", pressureMapper.evaluatePressure(773));
        assertEquals("Недопустимое значение давления", pressureMapper.evaluatePressure(-1));
        assertEquals("Недопустимое значение давления", pressureMapper.evaluatePressure(Double.NaN));
    }
}