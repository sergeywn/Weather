package com.volkov.weather.demo.mapper;

import org.springframework.stereotype.Component;

@Component
public class PressureMapper {

    public String evaluatePressure(double pressureInMmHg) {
        if (pressureInMmHg < 700 || pressureInMmHg > 800) {
            return "Недопустимое значение давления";
        }
        if (pressureInMmHg < 750) {
            return "Очень низкое давление";
        } else if (pressureInMmHg >= 750 && pressureInMmHg <= 755) {
            return "Низкое давление";
        } else if (pressureInMmHg > 755 && pressureInMmHg <= 759) {
            return "Среднее давление";
        } else if (pressureInMmHg > 759 && pressureInMmHg <= 767) {
            return "Нормальное давление";
        } else if (pressureInMmHg > 767 && pressureInMmHg <= 772) {
            return "Высокое давление";
        } else if (pressureInMmHg > 772) {
            return "Очень высокое давление";
        }
        // Добавьте возврат по умолчанию, чтобы покрыть все возможные случаи
        return "Недопустимое значение давления";
    }
}
