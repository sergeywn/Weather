package com.volkov.weather.demo.mapper;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = { PressureMapper.class }) // Только если необходим полный контекст Spring
@Tag("Mapper")
public class PressureMapperTest {

    @InjectMocks
    private PressureMapper pressureMapper;

    @Test
    public void testEvaluatePressure() {
        // Убедимся, что бин был успешно загружен
        assertThat(pressureMapper).isNotNull();

        // Проверим различные уровни давления
        assertThat(pressureMapper.evaluatePressure(749)).isEqualTo("Очень низкое давление");
        assertThat(pressureMapper.evaluatePressure(752)).isEqualTo("Низкое давление");
        assertThat(pressureMapper.evaluatePressure(757)).isEqualTo("Среднее давление");
        assertThat(pressureMapper.evaluatePressure(764)).isEqualTo("Нормальное давление");
        assertThat(pressureMapper.evaluatePressure(769)).isEqualTo("Высокое давление");
        assertThat(pressureMapper.evaluatePressure(773)).isEqualTo("Очень высокое давление");
        assertThat(pressureMapper.evaluatePressure(-1)).isEqualTo("Недопустимое значение давления");
        assertThat(pressureMapper.evaluatePressure(Double.NaN)).isEqualTo("Недопустимое значение давления");
    }
}