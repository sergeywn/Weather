package com.volkov.weather.demo.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeConverter {

    public String convertUtcToLocalTimezone(String input) {
        // Парсим строку в OffsetDateTime (UTC)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(input, formatter);

        // Конвертируем в ZonedDateTime с учетом временного пояса UTC
        ZonedDateTime utcDateTime = localDateTime.atZone(ZoneId.of("UTC"));

        // Переводим в указанный часовой пояс
        ZonedDateTime zonedDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Europe/Moscow"));

        // Формируем выходной формат
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return zonedDateTime.format(outputFormatter);
    }

    public String convertUtcToLocalTimezoneForWeek(String input) {
        // Парсим строку в OffsetDateTime (UTC)
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(input, parser);

        // Формируем выходной формат только для даты (без времени)
        DateTimeFormatter dateOnlyFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Проверка времени (для определения ночи)
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = localDateTime.format(timeFormatter);

        // Проверяем, является ли время ночью (00:00)
        if (formattedTime.equals("00:00")) {
            return localDateTime.format(dateOnlyFormatter) + " ночь";
        } else if (formattedTime.equals("06:00")) {
            return localDateTime.format(dateOnlyFormatter) + " утро";
        } else if (formattedTime.equals("12:00")) {
            return localDateTime.format(dateOnlyFormatter) + " день";
        } else {
            return localDateTime.format(dateOnlyFormatter) + " вечер";
        }
    }


}
