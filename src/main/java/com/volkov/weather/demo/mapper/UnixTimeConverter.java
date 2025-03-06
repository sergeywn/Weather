package com.volkov.weather.demo.mapper;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class UnixTimeConverter {

    /**
     * Преобразует Unix-время в читаемый формат.
     *
     * @param unixTime Время в формате Unix (количество секунд с 1 января 1970 года).
     * @return Строка в формате "dd.MM.yyyy HH:mm:ss".
     */
    public String convertUnixTime(long unixTime) {
        // 1. Преобразуем Unix-время в Instant
        Instant instant = Instant.ofEpochSecond(unixTime);

        // 2. Преобразуем Instant в ZonedDateTime (временная зона UTC)
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));

        // 3. Форматируем ZonedDateTime в строку
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return zonedDateTime.format(formatter);
    }

}
