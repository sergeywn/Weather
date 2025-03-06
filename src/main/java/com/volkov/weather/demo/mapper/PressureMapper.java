package com.volkov.weather.demo.mapper;

import org.springframework.stereotype.Component;

@Component
public class PressureMapper {

    public String evaluatePressure(double pressureInMmHg) {
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
        } else {
            return "Недопустимое значение давления";
        }
    }

}
