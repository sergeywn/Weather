package com.volkov.weather.demo.mapper;

import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {

    public String getCloudinessDescription(double cloudinessPercentage) {
        if (cloudinessPercentage >= 0 && cloudinessPercentage <= 20) {
            return "Ясная погода";
        } else if (cloudinessPercentage > 20 && cloudinessPercentage <= 40) {
            return "Немного облачно";
        } else if (cloudinessPercentage > 40 && cloudinessPercentage <= 60) {
            return "Частично облачная погода";
        } else if (cloudinessPercentage > 60 && cloudinessPercentage <= 80) {
            return "Преимущественно облачное небо";
        } else if (cloudinessPercentage > 80 && cloudinessPercentage <= 100) {
            return "Пасмурно";
        } else {
            return "Неверное значение облачности";
        }
    }

}
