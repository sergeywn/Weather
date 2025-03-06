package com.volkov.weather.demo.mapper;

import org.springframework.stereotype.Component;

@Component
public class WindMapper {

    public String getWindDirectionText(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5) {
            return "Север";
        } else if (degrees >= 22.5 && degrees < 67.5) {
            return "Северо-восток";
        } else if (degrees >= 67.5 && degrees < 112.5) {
            return "Восток";
        } else if (degrees >= 112.5 && degrees < 157.5) {
            return "Юго-восток";
        } else if (degrees >= 157.5 && degrees < 202.5) {
            return "Юг";
        } else if (degrees >= 202.5 && degrees < 247.5) {
            return "Юго-запад";
        } else if (degrees >= 247.5 && degrees < 292.5) {
            return "Запад";
        } else if (degrees >= 292.5 && degrees < 337.5) {
            return "Северо-запад";
        } else {
            return "Неопределено"; // Для покрытия крайних случаев
        }
    }

}
